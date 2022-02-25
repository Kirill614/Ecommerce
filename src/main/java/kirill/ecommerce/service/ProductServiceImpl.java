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
import kirill.ecommerce.models.request.AddProductRequest;
import kirill.ecommerce.models.request.ProductVariantRequest;
import kirill.ecommerce.repository.ProductRepository;
import kirill.ecommerce.repository.ProductVariantsRepository;
import kirill.ecommerce.service.supplier.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.*;
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
    public Product findByName(String name) {
        return cacheService.findByName(name);
    }

    @Override
    public List<Product> findAllProducts() {
        return cacheService.findAllProducts();
    }

    @Override
    public Product findById(int id) {
        return cacheService.findById(id);
    }

    @Override
    public List<ProductVariant> findProductVariants(int page, int size, String sort,
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

        return productVariantRepository.findAll(combinations, pageRequest).getContent();
    }

    @Override
    public List<Product> searchProduct(String keyword, int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);

        return cacheService.searchProduct(keyword, pageRequest);
    }

    @Override
    public Supplier addProductSupplier(AddProductRequest request){
        Supplier supplier = supplierService.getSupplier();
        ProductCategory category = categoryService.findByName(request.getProductDto().getCategory().getName());
        Product product = productConverter.convertFromDto(request.getProductDto());
        product.setCategory(category);
        product.setSupplier(supplier);
        if(supplier.getProducts() == null) supplier.setProducts(new ArrayList<Product>());
        supplier.getProducts().add(product);
        return supplierService.saveSupplier(supplier);
    }

//    @Override
//    public void addProductVariant(int productId, ProductVariant variant){
//        Product product = findById(productId);
//        variant.setProduct(product);
//        //productVariantRepository.save(variant);
//        product.getProductVariantList().add(variant);
//        productRepository.save(product);
//    }

    @Override
    public Product addProductVariant(ProductVariantRequest request){
        ProductVariant productVariant = variantConverter.convertFromDto(request.getProductVariantDto());
        Product product = findById(request.getProductId());
        productVariant.setProduct(product);
        product.getProductVariantList().add(productVariant);
        return productRepository.save(product);
    }

    @Override
    public List<Product> findByCategory(String category){
        return cacheService.findByCategory(category);
    }
}
