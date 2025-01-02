package org.shoppingmall.user.jwt;

public record JwtToken(
        String accessToken,
        String refreshToken
) {
}
