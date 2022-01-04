package kirill.ecommerce.service;

import kirill.ecommerce.models.entity.ProductCategory;
import kirill.ecommerce.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@CacheConfig(cacheNames = "product_category")
@Service
public class ProductCategoryCacheServiceImpl implements ProductCategoryCacheService{
    @Autowired
    ProductCategoryRepository productCategoryRep;

    @Override
    @Cacheable
    public List<ProductCategory> findAll(){
        return productCategoryRep.findAll();
    }

    @Override
    @Cacheable
    public ProductCategory findByName(String name){
        return productCategoryRep.findByName(name);
    }
}
