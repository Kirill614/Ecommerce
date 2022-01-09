package kirill.ecommerce.service.Cart;

public interface CartService {
    String addToCart(Long productVariantId, int amount) throws Exception;
}
