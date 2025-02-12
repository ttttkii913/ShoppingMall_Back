package org.shoppingmall.cart.api.dto.response;

import org.shoppingmall.cart.domain.Cart;

import java.util.List;

public record CartResDto(
        Long cartId,
        Long userId,
        List<CartItemResDto> cartItemResDtos
) {
    public CartResDto(Cart cart) {
        this(
                cart.getId(),
                cart.getUser().getId(),
                cart.getCartItems().stream()
                        .map(CartItemResDto::new)
                        .toList()
        );
    }
}
