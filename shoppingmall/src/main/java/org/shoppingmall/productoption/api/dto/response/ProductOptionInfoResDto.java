package org.shoppingmall.productoption.api.dto.response;

import org.shoppingmall.productoption.domain.ProductOptionColor;

public record ProductOptionInfoResDto(
        String size,
        int stock,
        ProductOptionColor productOptionColor
) {
}
