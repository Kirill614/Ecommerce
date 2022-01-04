package kirill.ecommerce.models.dto;

public class ProductVariantDto {
    private int id;
    private String composition;
    private Float price;
    private int stock;
    private int size;
    private String name;

    public ProductVariantDto( String composition, String name, Float price, int stock, int size) {
        //this.id = id;
        this.composition = composition;
        this.price = price;
        this.stock = stock;
        this.size = size;
        this.name = name;
    }

    public ProductVariantDto() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
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

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }
}
