package kirill.ecommerce.converter;

import kirill.ecommerce.models.dto.CategoryDto;
import kirill.ecommerce.models.entity.ProductCategory;
import org.springframework.stereotype.Component;

@Component
public class ProductCategoryConverter extends BaseConverter<ProductCategory, CategoryDto> {

    public ProductCategoryConverter() {
        super(ProductCategoryConverter::convertToDto, ProductCategoryConverter::convertToEntity);
    }

    private static CategoryDto convertToDto(ProductCategory category) {
        return new CategoryDto(category.getName());
    }

    private static ProductCategory convertToEntity(CategoryDto categoryDto) {
        return new ProductCategory(categoryDto.getName());
    }

}
