package org.shoppingmall.product.api.dto.response;

import lombok.Builder;
import org.shoppingmall.product.domain.Product;
import org.shoppingmall.product.domain.ProductStatus;

@Builder
public record ProductInfoResDto(
        Long productId,
        Long categoryId,
        Long userId,
        String name,
        Integer price,
        String info,
        Integer stock,
        ProductStatus productStatus
) {
    public static ProductInfoResDto from(Product product) {
        return ProductInfoResDto.builder()
                .productId(product.getId())
                .categoryId(product.getCategory().getId())
                .userId(product.getUser().getId())
                .name(product.getName())
                .price(product.getPrice())
                .info(product.getInfo())
                .stock(product.getStock())
                .productStatus(product.getProductStatus())
                .build();
    }
}
