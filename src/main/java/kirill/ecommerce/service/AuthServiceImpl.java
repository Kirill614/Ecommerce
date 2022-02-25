package kirill.ecommerce.service;

import kirill.ecommerce.models.EnumRole;
import kirill.ecommerce.models.entity.Customer;
import kirill.ecommerce.models.entity.Role;
import kirill.ecommerce.models.entity.Supplier;
import kirill.ecommerce.payload.SigninRequest;
import kirill.ecommerce.payload.SignupRequest;
import kirill.ecommerce.repository.CustomerRepository;
import kirill.ecommerce.repository.RoleRepository;
import kirill.ecommerce.repository.SupplierRepository;
import kirill.ecommerce.repository.UserRepository;
import kirill.ecommerce.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    AuthenticationManager authManager;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    JwtUtils jwtUtils;

    @Bean
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public String authenticate(SigninRequest request) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(request.getUsername(),
                request.getPassword());
        Authentication authentication = authManager.authenticate(auth);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        String authToken = jwtUtils.generateToken(username);
        System.out.println(authToken);
        return authToken;
    }

    @Override
    public ResponseEntity<?> regCustomer(SignupRequest request) {
        try {
            Customer customer = new Customer();
            customer.setUsername(request.getUsername());
            customer.setPassword(request.getPassword());
            customer.setMail(request.getMail());
            Role role = roleRepository.findByRole(EnumRole.ROLE_CUSTOMER)
                    .orElseThrow(() -> new Exception("role not found"));
            customer.getRoles().add(role);
            customerRepository.save(customer);
        } catch(Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok("");
    }

    @Override
    public ResponseEntity<?> regSupplier(SignupRequest request) {
        try {
            Supplier supplier = new Supplier();
            supplier.setUsername(request.getUsername());
            supplier.setPassword(request.getPassword());
            supplier.setMail(request.getMail());
            Role role = roleRepository.findByRole(EnumRole.ROLE_SUPPLIER)
                    .orElseThrow(() -> new Exception("role not found"));
            supplier.getRoles().add(role);
            supplierRepository.save(supplier);
        } catch(Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok("");
    }
}