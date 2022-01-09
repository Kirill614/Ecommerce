package kirill.ecommerce.service;

import kirill.ecommerce.ProductVariantSpecs;
import kirill.ecommerce.converter.ProductConverter;
import kirill.ecommerce.converter.ProductVariantConverter;
import kirill.ecommerce.models.dto.ProductDto;
import kirill.ecommerce.models.dto.ProductVariantDto;
import kirill.ecommerce.models.entity.Product;
import kirill.ecommerce.models.entity.ProductCategory;
import kirill.ecommerce.models.entity.ProductVariant;
import kirill.ecommerce.models.entity.Supplier;
import kirill.ecommerce.repository.ProductRepository;
import kirill.ecommerce.repository.ProductVariantsRepository;
import kirill.ecommerce.service.supplier.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductCacheServiceImpl cacheService;

    @Autowired
    ProductConverter productConverter;

    @Autowired
    ProductVariantConverter variantConverter;

    @Autowired
    ProductVariantsRepository productVariantRepository;

    @Autowired
    SupplierService supplierService;

    @Autowired
    ProductCategoryService categoryService;

    @Autowired
    ProductRepository productRepository;

    @Override
    public ProductDto findByName(String name) {
        Product product = cacheService.findByName(name);
        return productConverter.convertFromEntity(product);
    }

    @Override
    public List<ProductDto> findAllProducts() {
        return cacheService.findAllProducts().stream().map(productConverter::convertFromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Product findById(int id) {
        return cacheService.findById(id);
    }

    @Override
    public List<ProductVariantDto> findProductVariants(int page, int size, String sort,
                                                    String category, Float minPrice, Float maxPrice){
        PageRequest pageRequest;
        if(Objects.nonNull(sort) && !sort.isEmpty()){
            Sort sortObj = Sort.by(Sort.Direction.ASC, "");
            if(Objects.nonNull(sortObj)){
                pageRequest = PageRequest.of(page, size, sortObj);
            }
        }
        pageRequest = PageRequest.of(page, size);

        Specification<ProductVariant> combinations = Specification.where(ProductVariantSpecs.category(category)
                .and(ProductVariantSpecs.minPrice(minPrice))
                .and(ProductVariantSpecs.maxPrice(maxPrice)));

        return productVariantRepository.findAll(combinations, pageRequest)
                .stream()
                .map(variantConverter::convertFromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> searchProduct(String keyword, int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);

        return cacheService.searchProduct(keyword, pageRequest)
                .stream()
                .map(productConverter::convertFromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void addProductSupplier(Product product, String supplierUsername, String categoryName){
        Supplier supplier = supplierService.findSupplierByUsername(supplierUsername);
        ProductCategory category = categoryService.findByName(categoryName);
        product.setCategory(category);
        product.setSupplier(supplier);
        productRepository.save(product);
        supplier.getProducts().add(product);
        supplierService.saveSupplier(supplier);
    }

    @Override
    public void addProductVariant(int productId, ProductVariant variant){
        Product product = findById(productId);
        variant.setProduct(product);
        productVariantRepository.save(variant);
        product.getProductVariantList().add(variant);
        productRepository.save(product);
    }

}
