package es.storeapp.business.entities;

import es.storeapp.common.Constants;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = Constants.ORDER_ENTITY)
@Table(name = Constants.ORDERS_TABLE)
public class Order implements Serializable {

    private static final long serialVersionUID = -4089154240038598234L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// genera un numero de identificación para el id de orden
    private Long orderId;
    
    @Column(name = "name", nullable = false)//el nombre no puede ser falso (no es privado)
    String name;
    
    @Column(name = "timestamp", nullable = false)// el timestamp no puede ser nulo
    private Long timestamp;
    
    @Column(name = "price", nullable = false)// el precio no puede ser nulo
    private Integer price;
    
    @Column(name = "address", nullable = false)// la direccion no puede ser nula 
    private String address;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)//el estado de la orden será un string
    private OrderState state;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)// une el usuario con el que tiene el id de usuario correspondiente
    private User user;
    
    @OneToMany(mappedBy = "order")
    private List<OrderLine> orderLines = new ArrayList<>();// crea un arrayList con los pedidos 

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {// método que devuelve la información como string
        return String.format("Order{orderId=%s, name=%s, timestamp=%s, price=%s, address=%s, state=%s, user=%s, orderLines=%s}", 
            orderId, name, timestamp, price, address, state, user, orderLines);
    }    
    
}
