package kirill.ecommerce.service;

import kirill.ecommerce.models.dto.CategoryDto;
import kirill.ecommerce.models.entity.ProductCategory;
import kirill.ecommerce.models.response.ProductCategoryResponse;

import java.util.List;

public interface ProductCategoryService {
    List<CategoryDto> findAll();
    ProductCategory findByName(String name);
}
