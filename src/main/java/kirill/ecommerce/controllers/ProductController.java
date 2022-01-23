package kirill.ecommerce.controllers;

import kirill.ecommerce.converter.ProductConverter;
import kirill.ecommerce.converter.ProductVariantConverter;
import kirill.ecommerce.models.dto.ProductDto;
import kirill.ecommerce.models.dto.ProductVariantDto;
import kirill.ecommerce.models.entity.Product;
import kirill.ecommerce.models.entity.ProductCategory;
import kirill.ecommerce.models.entity.ProductVariant;
import kirill.ecommerce.models.request.AddProductRequest;
import kirill.ecommerce.models.request.ProductVariantRequest;
import kirill.ecommerce.repository.ProductRepository;
import kirill.ecommerce.repository.ProductVariantsRepository;
import kirill.ecommerce.service.ProductCategoryService;
import kirill.ecommerce.service.ProductService;
import kirill.ecommerce.service.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final ProductConverter productConverter;
    private final ProductVariantConverter productVariantConverter;

    @Autowired
    public ProductController(ProductService productService,
                             ProductConverter productConverter,
                             ProductVariantConverter productVariantConverter){
        this.productService = productService;
        this.productConverter = productConverter;
        this.productVariantConverter = productVariantConverter;
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @GetMapping("/productVariants/filter")
    ResponseEntity<?> findAllProductVariants(@RequestParam("page") int page,
                                             @RequestParam("size") int size,
                                             @RequestParam(name = "sort", required = false) String sort,
                                             @RequestParam(name = "category", required = false) String category,
                                             @RequestParam(name = "minPrice", required = false) Float minPrice,
                                             @RequestParam(name = "maxPrice", required = false) Float maxPrice) {

        List<ProductVariantDto> list = productService.findProductVariants(page, size, sort, category, minPrice, maxPrice);
        return ResponseEntity.ok(list);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @GetMapping("/search")
    ResponseEntity<?> searchProduct(@RequestParam("keyword") String keyword) {
        Logger log = LoggerFactory.getLogger(this.getClass());
        log.info("SEARCH");
        return ResponseEntity.ok(productService.searchProduct(keyword, 0, 20));
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    ResponseEntity<?> getAllProducts() {
        List<ProductDto> products = productService.findAllProducts();
        return ResponseEntity.ok(products);
    }

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
