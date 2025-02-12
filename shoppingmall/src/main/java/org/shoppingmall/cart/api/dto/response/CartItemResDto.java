package org.shoppingmall.cart.api.dto.response;

import org.shoppingmall.cartItem.domain.CartItem;

public record CartItemResDto (
        Long cartItemId,
        Long productId,
        Integer quantity
) {
public CartItemResDto(CartItem cartItem) {
    this(
            cartItem.getId(),
            cartItem.getProduct().getId(),
            cartItem.getQuantity()
    );
}
}
