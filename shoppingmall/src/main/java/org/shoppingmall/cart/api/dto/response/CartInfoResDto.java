package org.shoppingmall.cart.api.dto.response;

import org.shoppingmall.cartItem.domain.CartItem;

public record CartInfoResDto(
        Long cartItemId,
        Long productOptionId,
        Integer quantity,
        Integer price
) {
    public CartInfoResDto(CartItem cartItem) {
        this(
                cartItem.getId(),
                cartItem.getProductOption().getId(),
                cartItem.getQuantity(),
                cartItem.getProductOption().getProduct().getPrice()
        );
    }
}
