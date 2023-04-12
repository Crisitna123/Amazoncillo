package es.storeapp.business.services;

import es.storeapp.business.entities.CreditCard;
import es.storeapp.business.entities.Order;
import es.storeapp.business.entities.OrderLine;
import es.storeapp.business.entities.OrderState;
import es.storeapp.business.entities.Product;
import es.storeapp.business.entities.User;
import es.storeapp.business.exceptions.InstanceNotFoundException;
import es.storeapp.business.exceptions.InvalidStateException;
import es.storeapp.business.repositories.OrderLineRepository;
import es.storeapp.business.repositories.OrderRepository;
import es.storeapp.business.repositories.ProductRepository;
import es.storeapp.business.repositories.UserRepository;
import es.storeapp.business.utils.ExceptionGenerationUtils;
import es.storeapp.common.Constants;
import java.text.MessageFormat;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private OrderLineRepository orderLineRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    ExceptionGenerationUtils exceptionGenerationUtils;
    
    @Transactional()
    public Order create(User user, String name, String address, Integer price, List<Long> products) 
            throws InstanceNotFoundException {
        Order order = new Order();// Crea nueva orden
        order.setName(name);// Pone nombre
        order.setUser(user);// Pone user
        order.setAddress(address != null ? address : user.getAddress());// Si no pone dirección coge la del user
        order.setPrice(price);// Pone precio
        order.setState(OrderState.PENDING);// Establece el estado
        order.setTimestamp(System.currentTimeMillis());// Pone fecha y hora
        orderRepository.create(order);
        for (Long productId : products) {
            Product product = productRepository.findById(productId);// Pone id producto
            product.setSales(product.getSales() + 1);// Añade a las ventas del producto +1
            OrderLine orderLine = new OrderLine();// Crea nueva OrderLine
            orderLine.setPrice(product.getPrice());// Cambia el precio por el del producto
            orderLine.setProduct(product);// Pone el producto
            orderLine.setOrder(order);  // Pon el pedido
            orderLineRepository.create(orderLine);
        }
        return orderRepository.findById(order.getOrderId());// Devuelve el pedido por Id
    }
    
    @Transactional()
    public Order pay(User user, Long orderId, String creditCart, Integer cvv, 
                     Integer expirationMonth, Integer expirationYear, Boolean setAsDefault) 
        throws InstanceNotFoundException, InvalidStateException {
        Order order = orderRepository.findById(orderId);
        if(order.getState() != OrderState.PENDING) {
            if(logger.isWarnEnabled()) {
                logger.warn(MessageFormat.format("Trying to pay the order {0}", order));
            }
            throw exceptionGenerationUtils.toInvalidStateException(Constants.INVALID_STATE_EXCEPTION_MESSAGE);
        }
        order.setState(OrderState.COMPLETED);// Pone como completa la orden
        if(setAsDefault != null && setAsDefault) {// Si no está por defecto como nulo y sigue por defecto
            CreditCard card = new CreditCard();// Crea tarjeta
            card.setCard(creditCart);
            card.setCvv(cvv);
            card.setExpirationMonth(expirationMonth);
            card.setExpirationYear(expirationYear);
            user.setCard(card);
            userRepository.update(user);
        }
        return orderRepository.update(order);// Devuelve la actualización
    }
    
    @Transactional()
    public Order cancel(User user, Long orderId)// Cancela la orden
        throws InstanceNotFoundException, InvalidStateException {
        Order order = orderRepository.findById(orderId);// Busca por id
        if(order.getState() != OrderState.PENDING) {// Si no está Pendind
            throw exceptionGenerationUtils.toInvalidStateException(Constants.INVALID_STATE_EXCEPTION_MESSAGE);
        }
        order.setState(OrderState.CANCELLED);// Cambia el estado a cancelado
        return orderRepository.update(order);
    }
    
    @Transactional(readOnly = true)
    public List<Order> findByUserById(Long userId) throws InstanceNotFoundException {
        User user = userRepository.findById(userId);
        if(logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("Searching the orders of the user {0}", user.getEmail()));
        }
        return orderRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public Order findById(Long id) throws InstanceNotFoundException {
        return orderRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public boolean findIfUserBuyProduct(Long userId, Long productId) throws InstanceNotFoundException {
        User user = userRepository.findById(userId);
        if(logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("Checking if user {0} buy the product {1}", 
                user.getEmail(), productId));
        }
        return orderLineRepository.findIfUserBuyProduct(userId, productId);
    }
    
}
