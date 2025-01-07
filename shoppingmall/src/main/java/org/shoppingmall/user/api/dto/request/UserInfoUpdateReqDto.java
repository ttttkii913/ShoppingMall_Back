package org.shoppingmall.user.api.dto.request;

public record UserInfoUpdateReqDto(
        String name,
        String password,
        String email,
        String phone,
        String address
) {
}
