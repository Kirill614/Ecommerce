package kirill.ecommerce.models.entity;

import kirill.ecommerce.models.EnumRole;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "customer", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "mail"),
        @UniqueConstraint(columnNames = "password")
})
public class Customer {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "mail")
    private String mail;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "customer_role", joinColumns = @JoinColumn(name = "customer_id")
            , inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public Customer(String username, String password, String mail, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.roles = roles;
    }

    public Customer(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Cart getCart(){
        return this.cart;
    }

    public void setCart(Cart cart){
        this.cart = cart;
    }
}
