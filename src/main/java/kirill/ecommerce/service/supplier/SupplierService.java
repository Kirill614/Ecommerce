package kirill.ecommerce.service.supplier;

import kirill.ecommerce.models.entity.Supplier;

public interface SupplierService {
    Supplier findSupplierById(Long id);
    Supplier findSupplierByUsername(String username);
    Supplier saveSupplier(Supplier supplier);
    boolean existsByUsername(String username);
    Supplier getSupplier();
    boolean existsByMail(String mail);
}
