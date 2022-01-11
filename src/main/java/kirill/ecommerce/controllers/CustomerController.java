package kirill.ecommerce.controllers;

import kirill.ecommerce.converter.ProductConverter;
import kirill.ecommerce.converter.ProductVariantConverter;
import kirill.ecommerce.models.dto.ProductDto;
import kirill.ecommerce.models.dto.ProductVariantDto;
import kirill.ecommerce.models.request.AddToCartRequest;
import kirill.ecommerce.models.request.SearchRequest;
import kirill.ecommerce.repository.ProductRepository;
import kirill.ecommerce.repository.ProductVariantsRepository;
import kirill.ecommerce.service.Cart.CartServiceImpl;
import kirill.ecommerce.service.ProductCategoryService;
import kirill.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    ProductService productService;

    @Autowired
    CartServiceImpl cartService;

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
    @GetMapping("/searchProduct")
    ResponseEntity<?> searchProduct(@RequestParam("keyword") String keyword) {
        return ResponseEntity.ok(productService.searchProduct(keyword, 0, 100));
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    ResponseEntity<?> getAllProducts() {
        List<ProductDto> products = productService.findAllProducts();
        return ResponseEntity.ok(products);
    }

}
