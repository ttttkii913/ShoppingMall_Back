package org.shoppingmall.productoption.api.dto.request;

import lombok.Builder;
import org.shoppingmall.productoption.domain.ProductOptionColor;

@Builder
public record ProductOptionReqDto(
        String size,
        int stock,
        ProductOptionColor productOptionColor
) {
}
