package kirill.ecommerce.repository;

import kirill.ecommerce.models.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
