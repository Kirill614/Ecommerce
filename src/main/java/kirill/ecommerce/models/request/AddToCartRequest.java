package kirill.ecommerce.models.request;

public class AddToCartRequest {
    private Long productVariantId;
    private int amount;

    public AddToCartRequest(Long productVariantId, int amount) {
        this.productVariantId = productVariantId;
        this.amount = amount;
    }

    public AddToCartRequest(){ }

    public Long getProductVariantId() {
        return productVariantId;
    }

    public void setProductVariantId(Long productVariantId) {
        this.productVariantId = productVariantId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
