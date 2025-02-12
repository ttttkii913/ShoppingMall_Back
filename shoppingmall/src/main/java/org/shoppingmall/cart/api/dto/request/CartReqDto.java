package org.shoppingmall.cart.api.dto.request;

public record CartReqDto(

        Long productId,
        Integer quantity
) {
}
