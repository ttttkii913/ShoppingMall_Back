package org.shoppingmall.review.api.dto.request;

import org.shoppingmall.product.domain.Product;
import org.shoppingmall.review.domain.Review;
import org.shoppingmall.user.domain.User;

public record ReviewReqDto(
        String title,
        String content
) {
    public Review toEntity(String reviewImage, User user, Product product) {
        String image = (reviewImage != null) ? reviewImage : "";

        return Review.builder()
                .title(title)
                .content(content)
                .reviewImage(image)
                .user(user)
                .product(product)
                .build();
    }
}
