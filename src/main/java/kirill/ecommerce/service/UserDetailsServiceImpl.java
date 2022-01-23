package kirill.ecommerce.service;

import kirill.ecommerce.models.entity.Customer;
import kirill.ecommerce.models.entity.Supplier;
import kirill.ecommerce.service.customer.CustomerDetailsImpl;
import kirill.ecommerce.service.customer.CustomerService;
import kirill.ecommerce.service.supplier.SupplierDetailsImpl;
import kirill.ecommerce.service.supplier.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component("userService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CustomerService customerService;
    private final SupplierService supplierService;

    @Autowired
    public UserDetailsServiceImpl(CustomerService customerService,
                                  SupplierService supplierService){
        this.customerService = customerService;
        this.supplierService = supplierService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         if(customerService.existsByUsername(username)){
             Customer customer = customerService.findCustomerByUsername(username);
             return CustomerDetailsImpl.build(customer);
         }
         if(supplierService.existsByUsername(username)) {
             Supplier supplier = supplierService.findSupplierByUsername(username);
             return SupplierDetailsImpl.build(supplier);
         }
         return null;
    }
}
