package es.storeapp.business.entities;

import es.storeapp.common.Constants;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = Constants.CATEGORY_ENTITY)
@Table(name = Constants.CATEGORIES_TABLE)
public class Category implements Serializable {
    
    private static final long serialVersionUID = 340618567236100110L;
    
    @Id //dice que tiene que ser un id
    private Long categoryId;
    
    @Column(name = "name", nullable = false, unique = true)// no puede ser nulo, debe ser único y almacena el campo nombre
    private String name;
    
    @Column(name = "description", nullable = false)// no puede ser nulo
    private String description;
    
    @Column(name = "icon", nullable = false)// no puede no tener icono
    private String icon;
    
    @Column(name = "highlighted")
    private boolean highlighted;// puede estar resaltado

    public Long getCategoryId() {// devuelve el id de la categoria
        return categoryId;
    }

    public void setCategoryId(Long categoryId) { // cambia el id de la categoría
        this.categoryId = categoryId;
    }

    public String getName() {//devuelve nombre
        return name;
    }

    public void setName(String name) {//cambia nombre
        this.name = name;
    }

    public String getDescription() {// devuelve descripcion
        return description;
    }

    public void setDescription(String description) {// cambia descripcion
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isHighlighted() {// devuelve si está resaltado o no
        return highlighted;
    }

    public void setHighlighted(boolean highlighted) {// cambia el valor de si está resaltado entre true o false
        this.highlighted = highlighted;
    }

    @Override
    public String toString() {// devuelve los argumentos de la categoría
        return String.format("Category{categoryId=%s, name=%s, description=%s, icon=%s, highlighted=%s}", 
            categoryId, name, description, icon, highlighted);
    }
  
    
    
}
