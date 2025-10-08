package org.shoppingmall.review.api.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ReviewListResDto(
        List<ReviewInfoResDto> reviewResDtos
) {
    public static ReviewListResDto from(List<ReviewInfoResDto> reviewResDtos) {
        return ReviewListResDto.builder()
                .reviewResDtos(reviewResDtos)
                .build();
    }
}
