package kirill.ecommerce.models.request;

import kirill.ecommerce.models.dto.CartItemDto;
import kirill.ecommerce.models.entity.CartItem;

import java.util.List;

public class ConfirmCartRequest {
    private List<CartItemDto> cartItemList;
    private float totalCartPrice;

    public ConfirmCartRequest(List<CartItemDto> cartItemList, float totalCartPrice) {
        this.cartItemList = cartItemList;
        this.totalCartPrice = totalCartPrice;
    }

    public ConfirmCartRequest(){}

    public List<CartItemDto> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItemDto> cartItemList) {
        this.cartItemList = cartItemList;
    }

    public float getTotalCartPrice() {
        return totalCartPrice;
    }

    public void setTotalCartPrice(float totalCartPrice) {
        this.totalCartPrice = totalCartPrice;
    }
}
