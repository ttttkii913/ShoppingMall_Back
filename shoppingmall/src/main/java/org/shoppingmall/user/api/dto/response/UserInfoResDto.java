package org.shoppingmall.user.api.dto.response;

import lombok.Builder;
import org.shoppingmall.user.domain.User;
import org.shoppingmall.user.domain.UserStatus;

@Builder
public record UserInfoResDto(
        Long userId,
        String name,
        String email,
        String phone,
        String birthDay,
        String address,
        UserStatus userStatus,
        String accessToken,
        String refreshToken
) {
    public static UserInfoResDto from(User user, String accessToken, String refreshToken) {
        return UserInfoResDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .birthDay(user.getBirthDay())
                .address(user.getAddress())
                .userStatus(user.getUserStatus())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
