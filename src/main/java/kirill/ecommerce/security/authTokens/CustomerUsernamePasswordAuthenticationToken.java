package kirill.ecommerce.security.authTokens;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class CustomerUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {
    public CustomerUsernamePasswordAuthenticationToken(String username, String password){
        super(username, password);
    }
}
