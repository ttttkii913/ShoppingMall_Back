package org.shoppingmall.product.api.dto.request;

import org.shoppingmall.product.domain.Product;
import org.shoppingmall.product.domain.ProductStatus;

public record ProductUpdateReqDto(
        String name,
        Integer price,
        String info,
        Integer stock,
        ProductStatus productStatus
) {
    public Product toEntity() {
        return Product.builder()
                .name(name)
                .price(price)
                .info(info)
                .stock(stock)
                .productStatus(productStatus)
                .build();
    }
}
