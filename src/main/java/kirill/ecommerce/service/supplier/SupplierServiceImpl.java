package kirill.ecommerce.service.supplier;

import kirill.ecommerce.models.entity.Supplier;
import kirill.ecommerce.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository repository;

    public SupplierServiceImpl(SupplierRepository supplierRepository){
        this.repository = supplierRepository;
    }

    @Override
    public Supplier findSupplierById(Long id) {
        return repository.getById(id);
    }

    @Override
    public Supplier findSupplierByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public Supplier saveSupplier(Supplier supplier) {
        return repository.save(supplier);
    }

    @Override
    public boolean existsByUsername(String username){
        return repository.existsByUsername(username);
    }

    @Override
    public Supplier getSupplier(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return repository.findByUsername(username);
    }

    @Override
    public boolean existsByMail(String mail){
        return repository.existsByMail(mail);
    }
}
