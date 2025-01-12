package org.shoppingmall.product.api.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ProductListResDto(
        List<ProductInfoResDto> products
) {
    public static ProductListResDto from(List<ProductInfoResDto> products) {
        return ProductListResDto.builder()
                .products(products)
                .build();
    }
}
