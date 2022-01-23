package kirill.ecommerce.service.customer;

import kirill.ecommerce.models.entity.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerDetailsImpl implements UserDetails {

    private int id;
    private String username;
    private String password;
    private String mail;
    private List<GrantedAuthority> authorityList;

    public static CustomerDetailsImpl build(Customer customer){
        List<GrantedAuthority> authorities = customer.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().name()))
                .collect(Collectors.toList());

        return new CustomerDetailsImpl(customer.getId(), customer.getUsername(), customer.getPassword(),
                customer.getMail(), authorities);
    }

    public CustomerDetailsImpl(int id, String username, String password, String mail, List<GrantedAuthority> authorityList) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.authorityList = authorityList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityList;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
