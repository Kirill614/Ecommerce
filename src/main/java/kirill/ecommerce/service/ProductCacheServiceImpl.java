package kirill.ecommerce.service;

import kirill.ecommerce.converter.ProductVariantConverter;
import kirill.ecommerce.models.entity.Product;
import kirill.ecommerce.models.entity.ProductVariant;
import kirill.ecommerce.repository.ProductRepository;
import kirill.ecommerce.repository.ProductVariantsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@CacheConfig(cacheNames = "product")
@Component
public class ProductCacheServiceImpl implements ProductCacheService{
    private final ProductRepository productRepository;
    private final ProductVariantsRepository productVariantRepository;
    private final ProductVariantConverter productVariantConverter;

    @Autowired
    public ProductCacheServiceImpl(ProductRepository productRepository,
                                    ProductVariantsRepository productVariantRepository,
                                    ProductVariantConverter productVariantConverter){
        this.productRepository = productRepository;
        this.productVariantRepository = productVariantRepository;
        this.productVariantConverter = productVariantConverter;
    }

    @Override
    @Cacheable
    public Product findByName(String name){
        return productRepository.findByName(name);
    }

    @Override
    @Cacheable
    public Product findById(int id){
        return productRepository.findById(id);
    }

    @Override
    @Cacheable
    public List<Product> findAllProducts(){
        return productRepository.findAll();
    }

    @Override
    @Cacheable
    public List<Product> searchProduct(String keyword, Pageable pageable){
        List<Product> productVariants = productRepository.findAllByNameContainingIgnoreCase(keyword, pageable);

        return productVariants;
    }
}
