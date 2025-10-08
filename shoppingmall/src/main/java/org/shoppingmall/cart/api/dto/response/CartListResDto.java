package org.shoppingmall.cart.api.dto.response;

import org.shoppingmall.cart.domain.Cart;

import java.util.List;

public record CartListResDto(
        Long cartId,
        Long userId,
        List<CartInfoResDto> cartItemResDtos
) {
    public CartListResDto(Cart cart) {
        this(
                cart.getId(),
                cart.getUser().getId(),
                cart.getCartItems().stream()
                        .map(CartInfoResDto::new)
                        .toList()
        );
    }
}
