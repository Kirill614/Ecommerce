package kirill.ecommerce.service.supplier;

import kirill.ecommerce.models.entity.Supplier;
import kirill.ecommerce.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    SupplierRepository repository;

    @Override
    public Supplier findSupplierById(Long id) {
        return repository.getById(id);
    }

    @Override
    public Supplier findSupplierByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public void saveSupplier(Supplier supplier) {
        repository.save(supplier);
    }
}
