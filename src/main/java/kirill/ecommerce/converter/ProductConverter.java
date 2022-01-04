package kirill.ecommerce.converter;

import kirill.ecommerce.models.dto.CategoryDto;
import kirill.ecommerce.models.dto.ProductDto;
import kirill.ecommerce.models.entity.Product;
import kirill.ecommerce.models.entity.ProductCategory;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter extends BaseConverter<Product, ProductDto>{
    public ProductConverter(){
        super(ProductConverter::convertToDto, ProductConverter::convertToEntity);
    }

     private static ProductDto convertToDto(Product product){
          ProductDto productDto = new ProductDto();
          productDto.setName(product.getName());
          productDto.setSku(product.getSku());
          productDto.setDescription(product.getDescription());
          productDto.setCategory(new CategoryDto(product.getCategory().getName()));
          return productDto;
    }

    private static Product convertToEntity(ProductDto dto){
        Product product = new Product();
        ProductCategory category = new ProductCategory(dto.getCategory().getName());
        product.setName(dto.getName());
        product.setSku(dto.getSku());
        product.setDescription(dto.getDescription());
        product.setCategory(category);
        return product;
    }
}
