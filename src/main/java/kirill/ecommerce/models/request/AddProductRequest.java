package kirill.ecommerce.models.request;

import kirill.ecommerce.models.dto.ProductDto;
import kirill.ecommerce.models.entity.Product;

public class AddProductRequest {
    private ProductDto productDto;
    //private String username;

    public AddProductRequest(ProductDto productDto) {
        this.productDto = productDto;
        //this.username = username;
    }

    public AddProductRequest() { }

    public ProductDto getProductDto() {
        return productDto;
    }

    public void setProductDto(ProductDto productDto) {
        this.productDto = productDto;
    }

//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
}
