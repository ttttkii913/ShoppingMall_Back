package org.shoppingmall.cart.api.dto.request;

public record CartSaveReqDto(

        Long productOptionId,
        Integer quantity
) {
}
