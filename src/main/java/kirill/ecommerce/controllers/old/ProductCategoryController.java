package kirill.ecommerce.controllers.old;

import kirill.ecommerce.converter.ProductCategoryConverter;
import kirill.ecommerce.models.dto.CategoryDto;
import kirill.ecommerce.models.entity.ProductCategory;
import kirill.ecommerce.models.request.ProductCategoryRequest;
import kirill.ecommerce.models.response.ProductCategoryResponse;
import kirill.ecommerce.repository.ProductCategoryRepository;
import kirill.ecommerce.service.ProductCategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductCategoryController {

    @Autowired
    ProductCategoryServiceImpl productCategoryService;

    @Autowired
    ProductCategoryRepository categoryRepository;

    @Autowired
    ProductCategoryConverter categoryConverter;

    @GetMapping("/category")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    ResponseEntity<List<ProductCategoryResponse>> allCategories() {
        List<CategoryDto> categoryDtoList = productCategoryService.findAll();
        return ResponseEntity.ok(categoryDtoList.stream().map(ProductCategoryResponse::new)
                .collect(Collectors.toList()));
    }

    @PostMapping("/category/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<?> addCategory(@RequestBody ProductCategoryRequest categoryRequest) {
        try {
            categoryRepository.save(categoryConverter.convertFromDto(categoryRequest.getCategoryDto()));
            return ResponseEntity.ok("new category added!");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
