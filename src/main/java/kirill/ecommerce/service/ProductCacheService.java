package kirill.ecommerce.service;

import kirill.ecommerce.models.entity.Product;
import kirill.ecommerce.models.entity.ProductVariant;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductCacheService {
    Product findByName(String name);
    Product findById(int id);
    List<Product> findAllProducts();
    List<Product> searchProduct(String keyword, Pageable pageable);
}
