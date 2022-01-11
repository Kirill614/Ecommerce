package kirill.ecommerce.controllers;

import kirill.ecommerce.models.request.PostOrderRequest;
import kirill.ecommerce.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping("/post")
    ResponseEntity<?> postOrder(@RequestBody PostOrderRequest request){
        return ResponseEntity.ok(orderService.postOrder(request));
    }
}
