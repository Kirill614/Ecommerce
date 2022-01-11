package kirill.ecommerce.models.request;

import javax.validation.constraints.NotBlank;

public class PostOrderRequest {
    @NotBlank
    private String city;

    @NotBlank
    private String country;

    @NotBlank
    private String phone;

    public PostOrderRequest(String city, String country, String phone) {
        this.city = city;
        this.country = country;
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
