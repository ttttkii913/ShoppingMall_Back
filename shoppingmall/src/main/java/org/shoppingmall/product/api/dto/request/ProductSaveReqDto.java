package org.shoppingmall.product.api.dto.request;

import org.shoppingmall.category.domain.Category;
import org.shoppingmall.category.domain.CategoryType;
import org.shoppingmall.product.domain.Product;
import org.shoppingmall.product.domain.ProductStatus;
import org.shoppingmall.productoption.api.dto.request.ProductOptionReqDto;
import org.shoppingmall.user.domain.User;

import java.util.List;

public record ProductSaveReqDto(
        String name,
        Integer price,
        String info,
        Integer stock,
        ProductStatus productStatus,
        CategoryType categoryType,
        List<ProductOptionReqDto> options
) {
    public Product toEntity(User user, Category category) {
        return Product.builder()
                .name(name)
                .price(price)
                .info(info)
                .stock(stock)
                .productStatus(productStatus)
                .user(user)
                .category(category)
                .build();
    }
}
