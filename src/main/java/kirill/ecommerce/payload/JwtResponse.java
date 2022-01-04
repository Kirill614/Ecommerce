package kirill.ecommerce.payload;

import java.util.List;
import java.util.Set;

public class JwtResponse {
    private String token;
    private String username;
    private String password;
    private String mail;
    private Set<String> authoritiesStr;

    public JwtResponse(String token, String username, String password, String mail, Set<String> authoritiesStr) {
        this.token = token;
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.authoritiesStr = authoritiesStr;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Set<String> getAuthoritiesStr() {
        return authoritiesStr;
    }

    public void setAuthoritiesStr(Set<String> authoritiesStr) {
        this.authoritiesStr = authoritiesStr;
    }
}
