package kirill.ecommerce.controllers;

import kirill.ecommerce.payload.SigninRequest;
import kirill.ecommerce.payload.SignupRequest;
import kirill.ecommerce.repository.CustomerRepository;
import kirill.ecommerce.repository.SupplierRepository;
import kirill.ecommerce.repository.UserRepository;
import kirill.ecommerce.security.JwtHelper;
import kirill.ecommerce.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    SupplierRepository supplierRepository;

    Logger log = LoggerFactory.getLogger(Autowired.class);

    @PostMapping("/signup")
    ResponseEntity<?> registration(@RequestBody SignupRequest request) throws Exception {
        if (customerRepository.existsByUsername(request.getUsername()) ||
                supplierRepository.existsByUsername(request.getUsername()))
            return ResponseEntity.ok("This username already taken");
        if (customerRepository.existsByPassword(request.getPassword()) ||
                supplierRepository.existsByPassword(request.getPassword()))
            return ResponseEntity.ok("This password already taken");
        return authService.registration(request);
    }

    @PostMapping("/loginCustomer")
    ResponseEntity<?> authenticateCustomer(@Valid @RequestBody SigninRequest request) {
        return authService.authenticate(request);
    }

    @PostMapping("/loginSupplier")
        ResponseEntity<?> authenticateSupplier(@Valid @RequestBody SigninRequest request){
        return authService.authenticate(request);
    }
}
