package org.shoppingmall.product.api.dto.request;

import org.shoppingmall.category.domain.Category;
import org.shoppingmall.category.domain.CategoryType;
import org.shoppingmall.product.domain.Product;
import org.shoppingmall.product.domain.ProductStatus;
import org.shoppingmall.user.domain.User;

public record ProductSaveReqDto(
        String name,
        Integer price,
        String info,
        Integer stock,
        ProductStatus productStatus,
        CategoryType categoryType
) {
    public Product toEntity(String productImage, User user, Category category) {
        String image = (productImage != null) ? productImage : "";

        return Product.builder()
                .name(name)
                .price(price)
                .info(info)
                .stock(stock)
                .productStatus(productStatus)
                .productImage(image)
                .user(user)
                .category(category)
                .build();
    }
}
