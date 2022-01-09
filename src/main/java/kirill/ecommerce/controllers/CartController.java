package kirill.ecommerce.controllers;

import kirill.ecommerce.models.request.AddToCartRequest;
import kirill.ecommerce.service.Cart.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartServiceImpl cartService;

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping(value = "/add")
    ResponseEntity<?> addToCart(@RequestBody AddToCartRequest request) throws Exception {
       cartService.addToCart(request.getProductVariantId(), request.getAmount());
       return ResponseEntity.ok("product was added to cart");
    }

}
