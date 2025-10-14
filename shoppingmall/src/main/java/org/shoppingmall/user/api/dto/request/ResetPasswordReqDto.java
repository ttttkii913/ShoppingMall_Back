package org.shoppingmall.user.api.dto.request;

public record ResetPasswordReqDto(
        String email,
        String newPassword,
        String confirmPassword
) {
}
