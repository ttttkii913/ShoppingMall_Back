package org.shoppingmall.user.jwt;

public record LoginResDto(
        String accessToken,
        Long memberId
) {
}
