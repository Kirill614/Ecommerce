package kirill.ecommerce.service.customer;

import kirill.ecommerce.models.entity.Customer;
import kirill.ecommerce.repository.CustomerRepository;
import kirill.ecommerce.service.customer.CustomerDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomerDetailsServiceImpl implements UserDetailsService {

    @Autowired
    CustomerRepository repository;

    @Override
    public CustomerDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = repository.findByUsername(username);

        return CustomerDetailsImpl.build(customer);
    }
}
