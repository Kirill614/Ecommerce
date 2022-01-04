package kirill.ecommerce.models.entity;

import javax.persistence.*;

@Entity
@Table(name = "product_category", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class ProductCategory {
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "name")
    private String name;

    public ProductCategory(String name) {
        this.name = name;
    }

    public ProductCategory(){ }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
