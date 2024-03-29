package kirill.ecommerce.payload;

import java.util.Set;

public class SignupRequest {
    private String username;
    private String password;
    private String confirmPassword;
    private String mail;

    public SignupRequest(String username, String password, String confirmPassword,
                         String mail) {
        this.username = username;
        this.password = password;
        this.password = password;
        this.mail = mail;
    }

    public SignupRequest(){}

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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

}
