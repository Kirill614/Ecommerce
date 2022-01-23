package kirill.ecommerce.payload;

public class SigninRequest {
    private String username;
    private String password;
    //private String role;

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

    public SigninRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public SigninRequest(){}

//    public String getRole(){
//        return this.role;
//    }
//
//    public void setRole(String role){
//        this.role = role;
//    }
}
