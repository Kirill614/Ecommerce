package kirill.ecommerce.models.request;

import kirill.ecommerce.models.dto.ProductDto;
import kirill.ecommerce.models.entity.Product;

public class AddProductRequest {
    private ProductDto productDto;

    public AddProductRequest(ProductDto productDto) {
        this.productDto = productDto;
    }

    public AddProductRequest() { }

    public ProductDto getProductDto() {
        return productDto;
    }

    public void setProductDto(ProductDto productDto) {
        this.productDto = productDto;
    }
}
