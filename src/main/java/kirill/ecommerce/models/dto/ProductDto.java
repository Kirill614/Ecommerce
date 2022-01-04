package kirill.ecommerce.models.dto;

public class ProductDto {
    private String name;
    private String sku;
    private String description;
    private CategoryDto category;

    public ProductDto(String name, String sku, String description, CategoryDto category) {
        this.name = name;
        this.sku = sku;
        this.description = description;
        this.category = category;
    }

    public ProductDto(){ }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }
}
