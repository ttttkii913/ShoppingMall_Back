package org.shoppingmall.product.api.dto.response;

import lombok.Builder;
import org.shoppingmall.product.domain.Product;
import org.shoppingmall.product.domain.ProductStatus;
import org.shoppingmall.productoption.api.dto.response.ProductOptionInfoResDto;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record ProductInfoResDto(
        Long productId,
        Long categoryId,
        Long userId,
        String name,
        Integer price,
        String info,
        Integer stock,
        ProductStatus productStatus,
        List<ProductOptionInfoResDto> options
) {
    public static ProductInfoResDto from(Product product) {
        List<ProductOptionInfoResDto> options = product.getProductOptions()
                .stream()
                .map(opt -> new ProductOptionInfoResDto(
                        opt.getSize(),
                        opt.getStock(),
                        opt.getProductOptionColor()
                ))
                .collect(Collectors.toList());

        return ProductInfoResDto.builder()
                .productId(product.getId())
                .categoryId(product.getCategory().getId())
                .userId(product.getUser().getId())
                .name(product.getName())
                .price(product.getPrice())
                .info(product.getInfo())
                .stock(product.getStock())
                .productStatus(product.getProductStatus())
                .options(options)
                .build();
    }
}
