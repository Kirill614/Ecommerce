package kirill.ecommerce.service;

import kirill.ecommerce.models.dto.ProductDto;
import kirill.ecommerce.models.dto.ProductVariantDto;
import kirill.ecommerce.models.entity.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductDto findByName(String name);

    Product findById(int id);

    List<ProductDto> findAllProducts();

    List<ProductVariantDto> findProductVariants(int page, int size, String sort,
                                                String category, Float minPrice, Float maxPrice);

    List<ProductDto> searchProduct(String keyword, int page, int size);

}
