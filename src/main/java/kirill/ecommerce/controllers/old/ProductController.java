package kirill.ecommerce.controllers.old;

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
    @PostMapping("/add")
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

    @PreAuthorize("hasRole('ROLE_CUSTOMER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    ResponseEntity<?> getAllProducts() {
        List<ProductDto> products = productService.findAllProducts();

        return ResponseEntity.ok(products);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @GetMapping("/productVariants/all")
    ResponseEntity<?> findAllProductVariants(@RequestParam("page") int page,
                                             @RequestParam("size") int size,
                                             @RequestParam(name = "sort", required = false) String sort,
                                             @RequestParam(name = "category", required = false) String category,
                                             @RequestParam(name = "minPrice", required = false) Float minPrice,
                                             @RequestParam(name = "maxPrice", required = false) Float maxPrice){

         List<ProductVariantDto> list = productService.findProductVariants(page, size, sort, category, minPrice, maxPrice);
         return ResponseEntity.ok(list);
    }
}
