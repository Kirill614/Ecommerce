package kirill.ecommerce.repository;

import kirill.ecommerce.models.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);
    Product findById(int id);
    List<Product> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
}
