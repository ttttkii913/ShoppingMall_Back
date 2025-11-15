package org.shoppingmall.user.jwt;

import org.shoppingmall.user.domain.UserStatus;

public record LoginResDto(
        String accessToken,
        Long memberId,
        UserStatus userType
) {
}
