package kirill.ecommerce.security.providers;

import kirill.ecommerce.security.authTokens.CustomerUsernamePasswordAuthenticationToken;
import kirill.ecommerce.service.customer.CustomerDetailsImpl;
import kirill.ecommerce.service.customer.CustomerDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("customerProvider")
public class CustomerAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    CustomerDetailsServiceImpl customerService;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        CustomerDetailsImpl customerDetails = customerService.loadUserByUsername(username);
        if(Objects.nonNull(customerDetails)){
            if(passwordEncoder().matches(password, customerDetails.getPassword())){
            //if(password.equals(customerDetails.getPassword())){
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(customerDetails.getUsername(),
                        customerDetails.getPassword(), customerDetails.getAuthorities());
                return auth;
            }
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomerUsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
