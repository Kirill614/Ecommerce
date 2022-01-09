package kirill.ecommerce.service.customer;

import kirill.ecommerce.models.entity.Customer;

public interface CustomerService {
    Customer findCustomerById(int id);
}
