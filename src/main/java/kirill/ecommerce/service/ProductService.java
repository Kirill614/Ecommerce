package kirill.ecommerce.service;

import kirill.ecommerce.models.dto.ProductDto;
import kirill.ecommerce.models.dto.ProductVariantDto;
import kirill.ecommerce.models.entity.Product;
import kirill.ecommerce.models.entity.ProductVariant;
import kirill.ecommerce.models.entity.Supplier;
import kirill.ecommerce.models.request.AddProductRequest;
import kirill.ecommerce.models.request.ProductVariantRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Product findByName(String name);

    Product findById(int id);

    List<Product> findAllProducts();

    List<Product> findByCategory(String category);

    List<ProductVariant> findProductVariants(int page, int size, String sort,
                                                String category, Float minPrice, Float maxPrice);

    List<Product> searchProduct(String keyword, int page, int size);

    Supplier addProductSupplier(AddProductRequest request);

    Product addProductVariant(ProductVariantRequest request);

}
