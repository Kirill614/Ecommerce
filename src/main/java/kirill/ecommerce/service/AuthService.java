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
import kirill.ecommerce.security.authTokens.CustomerUsernamePasswordAuthenticationToken;
import kirill.ecommerce.security.JwtHelper;
import kirill.ecommerce.security.authTokens.SupplierUsernamePasswordAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthService {
    @Autowired
    RoleRepository roleRep;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    UserDetailsServiceImpl userService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    JwtHelper jwtHelper;

    public ResponseEntity<?> authenticate(SigninRequest request) {
        UsernamePasswordAuthenticationToken auth;
        String role = request.getRole();
        switch (request.getRole()) {
            case "customer":
                auth = new CustomerUsernamePasswordAuthenticationToken(request.getUsername(),
                        request.getPassword());
                break;
            case "supplier":
                auth = new SupplierUsernamePasswordAuthenticationToken(request.getUsername(),
                        request.getPassword());
                break;
            default:
                auth = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        }
        Authentication authentication = authManager.authenticate(auth);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtHelper.generateToken(request.getUsername());
        return ResponseEntity.ok(token);
    }

    public ResponseEntity<?> registration(SignupRequest request) {
        String role = request.getRole();
        if (Objects.nonNull(role) && !role.isEmpty()) {
            switch (role) {
                case "customer": {
                    Customer customer = new Customer();
                    customer.setUsername(request.getUsername());
                    customer.setPassword(passwordEncoder.encode(request.getPassword()));
                    customer.setMail(request.getMail());
                    Role customerRole = roleRepository.findByRole(EnumRole.ROLE_CUSTOMER).orElseThrow(() -> new RuntimeException("fsd"));
                    customer.getRoles().add(customerRole);
                    customerRepository.save(customer);
                    return ResponseEntity.ok("You are registered successfully!");
                }
                case "supplier": {
                    Supplier supplier = new Supplier();
                    supplier.setUsername(request.getUsername());
                    supplier.setPassword(passwordEncoder.encode(request.getPassword()));
                    supplier.setMail(request.getMail());
                    Role supplierRole = roleRepository.findByRole(EnumRole.ROLE_SUPPLIER)
                            .orElseThrow(() -> new RuntimeException("Role not found"));
                    supplier.getRoles().add(supplierRole);
                    supplierRepository.save(supplier);
                    return ResponseEntity.ok("You are registered successfully!");
                }
            }
        }
        return null;
    }






//    public ResponseEntity<?> authUser(SigninRequest request) {
//        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),
//                request.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
//        String authToken = jwtHelper.generateToken(user.getUsername());
//        Set<String> authoritiesStr = authentication.getAuthorities().stream()
//                .map(role -> role.getAuthority()).collect(Collectors.toSet());
//
//        return ResponseEntity.ok(new JwtResponse(authToken, user.getUsername(), user.getPassword(),
//                user.getMail(), authoritiesStr));
//
//    }


//    public ResponseEntity<?> registerUser(SignupRequest request) {
//        User user = new User(request.getUsername(), passwordEncoder.encode(request.getPassword()), request.getMail());
//        Set<String> rolesStr = request.getRoles();
//        Set<Role> roles = new HashSet<>();
//        if (rolesStr == null) {
//            Role customerRole = roleRep.findByRole(EnumRole.ROLE_CUSTOMER).orElseThrow(() -> new RuntimeException("role not found"));
//            roles.add(customerRole);
//        } else {
//            rolesStr.forEach(role -> {
//                switch (role) {
//                    case "customer": {
//                        Role customerRole = roleRep.findByRole(EnumRole.ROLE_CUSTOMER).orElseThrow(() -> new RuntimeException("role not found"));
//                        roles.add(customerRole);
//                        break;
//                    }
//                    case "admin": {
//                        Role adminRole = roleRep.findByRole(EnumRole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("role not found"));
//                        roles.add(adminRole);
//                        break;
//                    }
//                    case "supplier": {
//                        Role supplierRole = roleRep.findByRole(EnumRole.ROLE_SUPPLIER).orElseThrow(() -> new RuntimeException("role not found"));
//                        roles.add(supplierRole);
//                        break;
//                    }
//                }
//            });
//        }
//        user.setRoles(roles);
//        userRepository.save(user);
//        return ResponseEntity.ok("reg");
//        return null;
//    }
}
