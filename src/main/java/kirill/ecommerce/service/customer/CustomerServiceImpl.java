package kirill.ecommerce.service.customer;

import kirill.ecommerce.models.entity.Customer;
import kirill.ecommerce.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository repository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository){
        this.repository = customerRepository;
    }


    @Override
    public Customer findCustomerById(int id){
        return repository.getById((long)id);
    }

    @Override
    public Customer getCustomer(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return repository.findByUsername(auth.getPrincipal().toString());
    }

    @Override
    public boolean existsByUsername(String username){
        return repository.existsByUsername(username);
    }

    @Override
    public Customer findCustomerByUsername(String username){
        return repository.findByUsername(username);
    }

    @Override
    public boolean existsByMail(String mail) {
        return repository.existsByMail(mail);
    }
}
