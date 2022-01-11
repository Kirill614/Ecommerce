package kirill.ecommerce.service.order;

import kirill.ecommerce.models.request.PostOrderRequest;

public interface OrderService {
    String postOrder(PostOrderRequest orderRequest);
}
