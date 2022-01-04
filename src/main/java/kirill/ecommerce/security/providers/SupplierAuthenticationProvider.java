package kirill.ecommerce.security.providers;

import kirill.ecommerce.security.authTokens.SupplierUsernamePasswordAuthenticationToken;
import kirill.ecommerce.service.supplier.SupplierDetailsImpl;
import kirill.ecommerce.service.supplier.SupplierDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("supplierProvider")
public class SupplierAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    SupplierDetailsServiceImpl supplierService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        SupplierDetailsImpl supplier = (SupplierDetailsImpl) supplierService
                .loadUserByUsername(username);
        if (supplier != null) {
            if(passwordEncoder.matches(password, supplier.getPassword())){
                return new UsernamePasswordAuthenticationToken(username, password);
            }
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SupplierUsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
