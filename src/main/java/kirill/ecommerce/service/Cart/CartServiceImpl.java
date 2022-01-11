package kirill.ecommerce.service.Cart;

import kirill.ecommerce.models.dto.CartItemDto;
import kirill.ecommerce.models.entity.Cart;
import kirill.ecommerce.models.entity.CartItem;
import kirill.ecommerce.models.entity.Customer;
import kirill.ecommerce.models.entity.ProductVariant;
import kirill.ecommerce.models.request.ConfirmCartRequest;
import kirill.ecommerce.repository.CartItemRepository;
import kirill.ecommerce.repository.CartRepository;
import kirill.ecommerce.repository.CustomerRepository;
import kirill.ecommerce.repository.ProductVariantsRepository;
import kirill.ecommerce.service.customer.CustomerService;
import kirill.ecommerce.service.customer.CustomerServiceImpl;
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
    CustomerServiceImpl customerService;

    @Override
    public String addToCart(Long productVariantId, int amount) throws Exception {
        Customer customer = customerService.getCustomer();
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

    @Override
    public Boolean confirmCart(ConfirmCartRequest request) {
        Cart dbCart = customerService.getCustomer().getCart();
        if (Objects.isNull(dbCart) || Objects.isNull(dbCart.getCartItems())
                || dbCart.getCartItems().isEmpty()) return false;
        if (dbCart.getCartItems().size() != request.getCartItemList().size()) return false;

        if (!dbCart.getTotalPrice().equals(request.getTotalCartPrice())) return false;

        List<CartItemDto> itemList = request.getCartItemList();
        List<CartItem> dbItemList = dbCart.getCartItems();
        boolean itemsEqual = true;
        for (int i = 0; i < itemList.size(); i++) {
            if (!Objects.equals(itemList.get(i).getId(), dbItemList.get(i).getId()) ||
                    itemList.get(i).getAmount() != dbItemList.get(i).getAmount() ||
                    itemList.get(i).getProductVariantId() != dbItemList.get(i).getProductVariant().getId()) {
                itemsEqual = false;
                break;
            }
        }
        return itemsEqual;
    }

    @Override
    public String removeFromCart(Long cartItemId) {
        Cart cart = customerService.getCustomer().getCart();
        if(Objects.isNull(cart) || Objects.isNull(cart.getCartItems()) || cart.getCartItems().isEmpty()){
            return "cart is empty or not created yet!";
        }
        List<CartItem> itemsList = cart.getCartItems();
        Optional<CartItem> cartItem = cart.getCartItems()
                .stream()
                .filter(ci -> Objects.equals(ci.getId(), cartItemId))
                .findFirst();
        if(cartItem.isPresent()){
            itemsList.remove(cartItem.get());
            cart.setCartItems(itemsList);
            calculateTotalCartPrice(cart);
            cartRepository.save(cart);
        }
        return null;
    }

    @Override
    public void emptyCart(Customer customer){
        customer.setCart(null);
        customerRepository.save(customer);
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
