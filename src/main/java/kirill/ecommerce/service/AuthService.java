package kirill.ecommerce.service;

import kirill.ecommerce.payload.SigninRequest;
import kirill.ecommerce.payload.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    String authenticate(SigninRequest request);
    ResponseEntity<?> regCustomer(SignupRequest request);
    ResponseEntity<?> regSupplier(SignupRequest request);
}
