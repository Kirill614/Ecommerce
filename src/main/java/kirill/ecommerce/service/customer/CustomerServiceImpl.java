package kirill.ecommerce.service.customer;

import kirill.ecommerce.models.entity.Customer;
import kirill.ecommerce.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerServiceImpl implements CustomerService{
    @Autowired
    CustomerRepository repository;

    @Override
    public Customer findCustomerById(int id){
        return repository.getById((long)id);
    }
}
