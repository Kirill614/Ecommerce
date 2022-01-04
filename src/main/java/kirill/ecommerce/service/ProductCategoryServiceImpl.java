package kirill.ecommerce.service;

import kirill.ecommerce.converter.ProductCategoryConverter;
import kirill.ecommerce.models.dto.CategoryDto;
import kirill.ecommerce.models.entity.ProductCategory;
import kirill.ecommerce.models.response.ProductCategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService{

    @Autowired
    ProductCategoryCacheServiceImpl productCategoryCacheService;

    @Autowired
    ProductCategoryConverter categoryConverter;

    @Override
    public List<CategoryDto> findAll() {
        List<ProductCategory> categoryList = productCategoryCacheService.findAll();
        return categoryList.stream().map(item -> categoryConverter.convertFromEntity(item))
                .collect(Collectors.toList());
    }

    @Override
    public ProductCategory findByName(String name){
        ProductCategory category = productCategoryCacheService.findByName(name);
        //return categoryConverter.convertFromEntity(category);
        return category;
    }
}
