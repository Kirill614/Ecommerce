package kirill.ecommerce.models.entity;

import javax.persistence.*;

@Entity
@Table(name = "product_variant")
public class ProductVariant {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    //@JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "price")
    private Float price;

    @Column(name = "composition")
    private String composition;

    @Column(name = "stock")
    private int stock;

    @Column(name = "size")
    private int size;

    public ProductVariant(String composition, float price, int stock, int size) {
        this.composition = composition;
        this.price = price;
        this.stock = stock;
        this.size = size;
    }

    public ProductVariant(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
