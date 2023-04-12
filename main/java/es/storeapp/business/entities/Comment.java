package es.storeapp.business.entities;

import es.storeapp.common.Constants;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = Constants.COMMENT_ENTITY)
@Table(name = Constants.COMMENTS_TABLE)
public class Comment implements Serializable{

    private static final long serialVersionUID = -8821440815912953976L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// genera un valor 
    private Long commentId;
    
    @Column(name = "text", nullable = false)// el nombre no puede ser nulo
    private String text;
    
    @Column(name = "rating", nullable = false)// no puede sernulo
    private Integer rating;
    
    @Column(name = "timestamp", nullable = false)// la fecha/hora no puede ser nulo
    private Long timestamp;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)// junta el nombre de usuario con el id del usuario indicado
    private User user;// crea un usuario
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", nullable = false)// 
    private Product product;// crea un producto 

    public Long getCommentId() {// obtiene el id del comentario
        return commentId;
    }

    public void setCommentId(Long commentId) { // cambia el id del comentario
        this.commentId = commentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return String.format("Comment{commentId=%s, text=%s, rating=%s, timestamp=%s, user=%s, product=%s}", 
            commentId, text, rating, timestamp, user, product);
    }
    
}
