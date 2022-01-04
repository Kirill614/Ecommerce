package kirill.ecommerce.service;

import kirill.ecommerce.models.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryCacheService {
    List<ProductCategory> findAll();
    ProductCategory findByName(String name);
}
