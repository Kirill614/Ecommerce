package kirill.ecommerce.service.customer;

import kirill.ecommerce.models.entity.Customer;

public interface CustomerService {
    Customer findCustomerById(int id);
    Customer getCustomer();
    boolean existsByUsername(String username);
    Customer findCustomerByUsername(String username);
}
