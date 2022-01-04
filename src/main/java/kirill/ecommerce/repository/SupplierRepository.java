package kirill.ecommerce.repository;

import kirill.ecommerce.models.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
  boolean existsByUsername(String username);
  boolean existsByMail(String mail);
  boolean existsByPassword(String password);
  Supplier findByUsername(String name);
}
