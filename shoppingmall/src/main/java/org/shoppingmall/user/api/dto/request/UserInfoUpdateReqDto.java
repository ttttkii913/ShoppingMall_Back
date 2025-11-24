package org.shoppingmall.user.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserInfoUpdateReqDto(
        @NotBlank(message = "이름을 필수로 입력해야 합니다.")
        @Size(min = 2, max = 15, message = "2자 이상, 15자 이하로 입력하세요.")
        String name,

        @Size(min = 8, message = "8자 이상 입력하세요.")
        String password,

        @NotBlank(message = "이메일을 필수로 입력해야 합니다.")
        @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식이 맞지 않습니다.")
        String email,

        @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "입력한 번호 형식이 맞지 않습니다.")
        String phone,

        String address,

        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "입력한 생일 형식이 맞지 않습니다.")
        String birthDay
) {
}
