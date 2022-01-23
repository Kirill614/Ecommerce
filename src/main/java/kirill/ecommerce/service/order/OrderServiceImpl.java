package kirill.ecommerce.service.order;

import kirill.ecommerce.models.entity.Cart;
import kirill.ecommerce.models.entity.Customer;
import kirill.ecommerce.models.entity.Order;
import kirill.ecommerce.models.entity.OrderDetail;
import kirill.ecommerce.models.request.PostOrderRequest;
import kirill.ecommerce.repository.OrderRepository;
import kirill.ecommerce.service.Cart.CartService;
import kirill.ecommerce.service.customer.CustomerService;
import kirill.ecommerce.service.customer.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

@Component
public class OrderServiceImpl implements OrderService{
    private final CustomerService customerService;
    private final OrderRepository orderRepository;
    private final CartService cartService;

    public OrderServiceImpl(CustomerService customerService,
                            OrderRepository orderRepository,
                            CartService cartService){
        this.customerService = customerService;
        this.orderRepository = orderRepository;
        this.cartService = cartService;
    }
    @Override
    public String postOrder(PostOrderRequest request){
        Customer customer = customerService.getCustomer();
        Cart cart = customer.getCart();
        if(Objects.isNull(cart) || Objects.isNull(cart.getCartItems()) || cart.getCartItems().isEmpty()){
            throw new IllegalArgumentException("Your cart is not valid");
        }
        if(cart.getCartItems().stream().anyMatch(ci -> ci.getProductVariant().getStock() < ci.getAmount())){
            throw new IllegalArgumentException("Product in your cart is out of stock!");
        }
        Order order = new Order();
        order.setCity(request.getCity());
        order.setCountry(request.getCountry());
        order.setPhone(request.getPhone());
        order.setOrderDetailList(new ArrayList<OrderDetail>());
        order.setDate(new Date());
        order.setTotalPrice(cart.getTotalPrice());
        order.setCustomer(customer);

        cart.getCartItems().forEach(ci -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProductVariantId(ci.getProductVariant().getId());
            orderDetail.setAmount(ci.getAmount());
            orderDetail.setOrder(order);
            order.getOrderDetailList().add(orderDetail);
        });
        cartService.emptyCart(customer);
        orderRepository.save(order);
        return "order was created";

    }
}
