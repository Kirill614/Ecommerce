package kirill.ecommerce.models.dto;

public class CartItemDto {
    private Long id;
    private int productVariantId;
    private int amount;

    public CartItemDto(int productVariantId, int amount) {
        this.productVariantId = productVariantId;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getProductVariantId() {
        return productVariantId;
    }

    public void setProductVariantId(int productVariantId) {
        this.productVariantId = productVariantId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
