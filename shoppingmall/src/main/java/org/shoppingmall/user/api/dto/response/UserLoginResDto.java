package org.shoppingmall.user.api.dto.response;

import lombok.Builder;
import org.shoppingmall.user.domain.User;

@Builder
public record UserLoginResDto(
        String name,
        String email,
        String accessToken,
        String refreshToken
) {
    public static UserLoginResDto of(User user, String accessToken, String refreshToken) {
        return UserLoginResDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
