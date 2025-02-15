package org.shoppingmall.review.api.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ReviewListResDto(
        List<ReviewResDto> reviewResDtos
) {
    public static ReviewListResDto from(List<ReviewResDto> reviewResDtos) {
        return ReviewListResDto.builder()
                .reviewResDtos(reviewResDtos)
                .build();
    }
}
