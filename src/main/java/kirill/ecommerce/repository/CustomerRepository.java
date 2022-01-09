package kirill.ecommerce.repository;

import kirill.ecommerce.models.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByUsername(String username);
    Optional<Customer> findById(int id);
    boolean existsByUsername(String username);
    boolean existsByMail(String mail);
    boolean existsByPassword(String password);
}
