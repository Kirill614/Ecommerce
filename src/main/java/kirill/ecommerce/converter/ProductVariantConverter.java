package kirill.ecommerce.converter;

import kirill.ecommerce.models.dto.ProductVariantDto;
import kirill.ecommerce.models.entity.ProductVariant;
import org.springframework.stereotype.Component;

@Component
public class ProductVariantConverter extends BaseConverter<ProductVariant, ProductVariantDto> {
    public ProductVariantConverter() {
        super(ProductVariantConverter::convertToDto, ProductVariantConverter::convertToEntity);
    }

    private static ProductVariantDto convertToDto(ProductVariant entity) {
        ProductVariantDto dto = new ProductVariantDto();
        dto.setComposition(entity.getComposition());
        dto.setPrice(entity.getPrice());
        dto.setStock(entity.getStock());
        dto.setSize(entity.getSize());
        dto.setName(entity.getProduct().getName());
        return dto;
    }

    private static ProductVariant convertToEntity(ProductVariantDto dto) {
        ProductVariant entity = new ProductVariant();
        entity.setComposition(dto.getComposition());
        entity.setPrice(dto.getPrice());
        entity.setStock(dto.getStock());
        entity.setSize(dto.getSize());
        return entity;
    }
}
