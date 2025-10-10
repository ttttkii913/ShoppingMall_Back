package org.shoppingmall.productoption.api.dto.request;

import org.shoppingmall.productoption.domain.ProductOptionColor;

public record ProductOptionUpdateReqDto(
        Long productOptionId,
        String size,
        int stock,
        ProductOptionColor productOptionColor
) {
}
