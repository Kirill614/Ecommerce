package kirill.ecommerce.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import kirill.ecommerce.models.dto.CartItemDto;
import kirill.ecommerce.models.request.AddToCartRequest;
import kirill.ecommerce.models.request.ConfirmCartRequest;
import kirill.ecommerce.service.Cart.CartService;
import kirill.ecommerce.service.Cart.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping(value = "/add")
    ResponseEntity<?> addToCart(@RequestBody AddToCartRequest request) throws Exception {
        cartService.addToCart(request.getProductVariantId(), request.getAmount());
        return ResponseEntity.ok("product was added to cart");
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping("/confirmCart")
    ResponseEntity<?> confirmCart(@RequestBody ConfirmCartRequest request) throws JsonProcessingException {
        return ResponseEntity.ok(cartService.confirmCart(request));
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping("/remove")
    ResponseEntity<?> removeFromCart(@RequestParam Long itemId) throws JsonProcessingException {
        return ResponseEntity.ok(cartService.removeFromCart(itemId));
    }
}
