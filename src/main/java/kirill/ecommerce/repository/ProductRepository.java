package kirill.ecommerce.repository;

import kirill.ecommerce.models.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>,
        JpaSpecificationExecutor<Product> {
    Product findByName(String name);
    Product findById(int id);
    List<Product> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
}
