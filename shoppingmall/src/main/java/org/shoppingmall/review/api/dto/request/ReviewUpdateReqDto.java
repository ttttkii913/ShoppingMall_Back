package org.shoppingmall.review.api.dto.request;

import org.shoppingmall.review.domain.Review;
import org.shoppingmall.user.domain.User;

public record ReviewUpdateReqDto(
        String title,
        String content
) {
    public Review toEntity(String reviewImage, User user) {
        return Review.builder()
                .title(title)
                .content(content)
                .reviewImage(reviewImage)
                .user(user)
                .build();
    }
}
