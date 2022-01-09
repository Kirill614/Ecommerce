package kirill.ecommerce.repository;

import kirill.ecommerce.models.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
