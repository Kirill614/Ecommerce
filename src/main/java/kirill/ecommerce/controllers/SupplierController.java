package kirill.ecommerce.controllers;

import kirill.ecommerce.converter.ProductConverter;
import kirill.ecommerce.converter.ProductVariantConverter;
import kirill.ecommerce.models.entity.Product;
import kirill.ecommerce.models.entity.ProductCategory;
import kirill.ecommerce.models.entity.ProductVariant;
import kirill.ecommerce.models.entity.Supplier;
import kirill.ecommerce.models.request.AddProductRequest;
import kirill.ecommerce.models.request.ProductVariantRequest;
import kirill.ecommerce.repository.ProductRepository;
import kirill.ecommerce.repository.ProductVariantsRepository;
import kirill.ecommerce.repository.SupplierRepository;
import kirill.ecommerce.service.ProductCategoryService;
import kirill.ecommerce.service.ProductService;
import kirill.ecommerce.service.supplier.SupplierDetailsImpl;
import kirill.ecommerce.service.supplier.SupplierDetailsServiceImpl;
import kirill.ecommerce.service.supplier.SupplierServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    SupplierServiceImpl supplierService;

    @Autowired
    SupplierRepository supplierRepository;

    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    @PostMapping("/product/add")
    ResponseEntity<?> addProduct(@RequestBody AddProductRequest productRequest) {
        try {
            Product product = productConverter.convertFromDto(productRequest.getProductDto());

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String supplierUsername = auth.getPrincipal().toString();

            String categoryName = productRequest.getProductDto().getCategory().getName();
            productService.addProductSupplier(product, supplierUsername, categoryName);
            return ResponseEntity.ok("added new product!");
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    @PostMapping("/productVariants/add")
    ResponseEntity<?> addProductVariants(@RequestBody ProductVariantRequest variantRequest) {
        try {
            ProductVariant variant = productVariantConverter.convertFromDto(variantRequest.getProductVariantDto());
            productService.addProductVariant(variantRequest.getProductId(), variant);
            return ResponseEntity.ok("added new product variant!");
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
