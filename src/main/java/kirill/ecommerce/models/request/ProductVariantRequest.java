package kirill.ecommerce.models.request;

import kirill.ecommerce.models.dto.ProductVariantDto;

public class ProductVariantRequest {
    private int productId;
    private ProductVariantDto productVariantDto;

    public ProductVariantRequest(int productId, ProductVariantDto productVariantDto) {
        this.productId = productId;
        this.productVariantDto = productVariantDto;
    }

    public ProductVariantRequest() { }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public ProductVariantDto getProductVariantDto() {
        return productVariantDto;
    }

    public void setProductVariantDto(ProductVariantDto productVariantDto) {
        this.productVariantDto = productVariantDto;
    }
}
