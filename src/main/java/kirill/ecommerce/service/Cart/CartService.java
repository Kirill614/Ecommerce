package kirill.ecommerce.service.Cart;

import kirill.ecommerce.models.entity.Customer;
import kirill.ecommerce.models.request.ConfirmCartRequest;

public interface CartService {
    String addToCart(Long productVariantId, int amount) throws Exception;
    Boolean confirmCart(ConfirmCartRequest cart);
    String removeFromCart(Long cartItemId);
    void emptyCart(Customer customer);
}
