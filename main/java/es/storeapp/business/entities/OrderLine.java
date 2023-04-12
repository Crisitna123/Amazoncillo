package es.storeapp.business.entities;

import es.storeapp.common.Constants;
import java.io.Serializable;
import java.text.MessageFormat;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = Constants.ORDER_LINE_ENTITY)
@Table(name = Constants.ORDER_LINES_TABLE)
public class OrderLine implements Serializable {
    
    private static final long serialVersionUID = -1663947782096663051L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// genera un valor aleatorio para identificar la orden
    private Long orderLineId;
    
    @Column(name = "price", nullable = false)
    private Integer price;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", nullable = false)//el id del producto se relaciona con el producto
    private Product product;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId", nullable = false)// el id del pedido se relaciona con la orden
    private Order order;

    public Long getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(Long orderLineId) {
        this.orderLineId = orderLineId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {// saca los argumentos
        return MessageFormat.format("OrderLine{orderLineId={0}, price={1}, product={2}, order={3}}", 
            orderLineId, price, product, order);
    }
   
    
    
}
