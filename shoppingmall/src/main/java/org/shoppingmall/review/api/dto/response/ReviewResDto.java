package org.shoppingmall.review.api.dto.response;

import lombok.Builder;
import org.shoppingmall.review.domain.Review;

@Builder
public record ReviewResDto(
        Long productId,
        Long userId,
        String title,
        String content,
        String reviewImage
) {
    // product랑 매핑 연결 안 되어있으면 productId 안 불러와짐
    public static ReviewResDto from(Review review) {
        return ReviewResDto.builder()
                .productId(review.getProduct().getId())
                .userId(review.getUser().getId())
                .title(review.getTitle())
                .content(review.getContent())
                .reviewImage(review.getReviewImage())
                .build();
    }
}
