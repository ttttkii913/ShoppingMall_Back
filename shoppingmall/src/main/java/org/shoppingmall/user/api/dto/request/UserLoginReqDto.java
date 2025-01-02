package org.shoppingmall.user.api.dto.request;

public record UserLoginReqDto(
        String email,
        String password
) {
}
