package org.shoppingmall.product.api.dto.response;

import lombok.Builder;
import org.shoppingmall.product.domain.ProductImage;

@Builder
public record ProductImageResDto(
        Long id,
        String imgUrl,
        Boolean isMainImg,
        Integer imageOrder
) {
    public static ProductImageResDto from(ProductImage image) {
        return new ProductImageResDto(
                image.getId(),
                image.getImgUrl(),
                image.getIsMainImg(),
                image.getImageOrder()
        );
    }
}
