package es.storeapp.business.entities;

import es.storeapp.common.Constants;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = Constants.USER_ENTITY)
@Table(name = Constants.USERS_TABLE)
public class User implements Serializable {

    private static final long serialVersionUID = 570528466125178223L;

    public User() {// Crea un usuario vacío
    }

    public User(String name, String email, String password, String address, String image) {// Crea usuario con todo
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.image = image;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;// Genera in id
    
    @Column(name = "name", nullable = false, unique = false)
    private String name;// Almacena el nombre
    
    @Column(name = "email", nullable = false, unique = true)
    private String email;// Almacena el email y tiene que ser único
    
    @Column(name = "password", nullable = false)
    private String password;// Almacena la contraseña que no puede ser nula

    @Column(name = "address", nullable = false)
    private String address;// Almacena la dirección que no puede ser nula

    @Column(name = "resetPasswordToken")
    private String resetPasswordToken;// Almacena el token de resset de la contraseña
    
    @Embedded
    @AttributeOverrides(value = {
        @AttributeOverride(name = "card", column = @Column(name = "card")),// Almacena tarjeta
        @AttributeOverride(name = "cvv", column = @Column(name = "CVV")),// Almacena cvv
        @AttributeOverride(name = "expirationMonth", column = @Column(name = "expirationMonth")),// Almacena mes de expiración
        @AttributeOverride(name = "expirationYear", column = @Column(name = "expirationYear"))// Almacena año de expiración
    })
    private CreditCard card;// Almacena la tarjeta
    
    @Column(name = "image")
    private String image;// Almacena la imagen
    
    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();// Almacena los comentarios del usuario en un ArrayList
    
    public Long getUserId() {// Devuelve el id de usuario
        return userId;
    }

    public void setUserId(Long userId) {// Cambia el id de usuario
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public CreditCard getCard() {// Debería ser privated?
        return card;
    }

    public void setCard(CreditCard card) {
        this.card = card;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getResetPasswordToken() {// Deberia ser privated?
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {// Debería ser privated?
        this.resetPasswordToken = resetPasswordToken;
    }

    @Override
    public String toString() {
        return String.format("User{userId=%s, name=%s, email=%s, password=%s, address=%s, resetPasswordToken=%s, card=%s, image=%s}", 
            userId, name, email, password, address, resetPasswordToken, card, image);
    }

}
