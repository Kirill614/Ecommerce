package kirill.ecommerce.service.supplier;

import kirill.ecommerce.models.entity.Supplier;
import kirill.ecommerce.repository.SupplierRepository;
import kirill.ecommerce.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class SupplierDetailsServiceImpl implements UserDetailsService {

    @Autowired
    SupplierRepository supplierRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Supplier supplier = supplierRepository.findByUsername(username);
        return SupplierDetailsImpl.build(supplier);
    }
}
