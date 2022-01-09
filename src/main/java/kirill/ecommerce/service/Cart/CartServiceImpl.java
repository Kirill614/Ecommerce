package kirill.ecommerce.service.Cart;

import kirill.ecommerce.models.entity.Cart;
import kirill.ecommerce.models.entity.CartItem;
import kirill.ecommerce.models.entity.Customer;
import kirill.ecommerce.models.entity.ProductVariant;
import kirill.ecommerce.repository.CartItemRepository;
import kirill.ecommerce.repository.CartRepository;
import kirill.ecommerce.repository.CustomerRepository;
import kirill.ecommerce.repository.ProductVariantsRepository;
import kirill.ecommerce.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class CartServiceImpl implements CartService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductVariantsRepository variantsRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Override
    public String addToCart(Long productVariantId, int amount) throws Exception {
        Customer customer = getCustomer();
        Cart cart = customer.getCart();

        ProductVariant productVariant = variantsRepository.findById(productVariantId)
                .orElseThrow(() -> new Exception("product variant not found"));

        if (productVariant.getStock() < amount) {
            throw new Exception("amount");
        }

        if (Objects.nonNull(cart) && Objects.nonNull(cart.getCartItems()) && !cart.getCartItems().isEmpty()) {
            Optional<CartItem> cartItem = cart.getCartItems()
                    .stream()
                    .filter(ci -> (ci.getProductVariant().getId().equals(productVariantId)))
                    .findFirst();
            if (cartItem.isPresent()) {
                if (cartItem.get().getProductVariant().getStock() < cartItem.get().getAmount() + amount) {
                    throw new Exception("Product does not have desired stock");
                }
                cartItem.get().setAmount(cartItem.get().getAmount() + amount);
                cartRepository.save(cart);
                return "cart item was changed";
            }
        }

        if (Objects.isNull(cart)) {
            cart = new Cart();
            cart.setCartItems(new ArrayList<CartItem>());
        }
        setCart(cart, customer, productVariant, amount);
        return "product was added to cart";
    }

    private Customer getCustomer() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return customerRepository.findByUsername(auth.getPrincipal().toString());
    }

    private void setCart(Cart cart, Customer customer, ProductVariant productVariant, int amount) {
        cart.setCustomer(customer);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setAmount(amount);
        cartItem.setProductVariant(productVariant);

        cart.getCartItems().add(cartItem);
        calculateTotalCartPrice(cart);

        customer.setCart(cart);
        cartRepository.save(cart);
    }

    private void calculateTotalCartPrice(Cart cart) {
        cart.setTotalPrice(0F);
        cart.getCartItems().forEach(ci ->
                cart.setTotalPrice(cart.getTotalPrice() + ci.getProductVariant().getPrice() * ci.getAmount()));
    }
}
