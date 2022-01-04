package kirill.ecommerce.controllers;

import kirill.ecommerce.converter.ProductConverter;
import kirill.ecommerce.converter.ProductVariantConverter;
import kirill.ecommerce.models.entity.Product;
import kirill.ecommerce.models.entity.ProductCategory;
import kirill.ecommerce.models.entity.ProductVariant;
import kirill.ecommerce.models.request.AddProductRequest;
import kirill.ecommerce.models.request.ProductVariantRequest;
import kirill.ecommerce.repository.ProductRepository;
import kirill.ecommerce.repository.ProductVariantsRepository;
import kirill.ecommerce.service.ProductCategoryService;
import kirill.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    ProductConverter productConverter;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductVariantsRepository productVariantRepository;

    @Autowired
    ProductCategoryService categoryService;

    @Autowired
    ProductService productService;

    @Autowired
    ProductVariantConverter productVariantConverter;

    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    @PostMapping("/product/add")
    ResponseEntity<?> addProduct(@RequestBody AddProductRequest productRequest) {
        try {
            ProductCategory category = categoryService.findByName(productRequest.getProductDto()
                    .getCategory().getName());
            Product product = productConverter.convertFromDto(productRequest.getProductDto());
            product.setCategory(category);

            productRepository.save(product);
            return ResponseEntity.ok("added new product!");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    @PostMapping("/productVariants/add")
    ResponseEntity<?> addProductVariants(@RequestBody ProductVariantRequest variantRequest) {
        ProductVariant productVariant = productVariantConverter
                .convertFromDto(variantRequest.getProductVariantDto());
        Product product = productService.findById(variantRequest.getProductId());
        productVariant.setProduct(product);
        productVariantRepository.save(productVariant);
        product.getProductVariantList().add(productVariant);
        productRepository.save(product);
        return ResponseEntity.ok("");
    }
}
