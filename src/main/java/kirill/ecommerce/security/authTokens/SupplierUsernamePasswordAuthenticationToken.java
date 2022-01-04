package kirill.ecommerce.security.authTokens;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class SupplierUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {
    public SupplierUsernamePasswordAuthenticationToken(String username, String password){
        super(username, password);
    }
}
