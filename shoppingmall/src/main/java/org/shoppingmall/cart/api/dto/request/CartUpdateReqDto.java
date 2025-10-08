package org.shoppingmall.cart.api.dto.request;

import org.shoppingmall.productoption.domain.ProductOptionColor;

public record CartUpdateReqDto(
        Long productOptionId,
        int quantity,
        String size,
        ProductOptionColor productOptionColor
) {
}
