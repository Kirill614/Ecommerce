package kirill.ecommerce.service.supplier;

import kirill.ecommerce.models.entity.Supplier;

public interface SupplierService {
    Supplier findSupplierById(Long id);
    Supplier findSupplierByUsername(String username);
    void saveSupplier(Supplier supplier);
}
