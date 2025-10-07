package org.shoppingmall.user.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.shoppingmall.user.domain.User;
import org.shoppingmall.user.domain.UserStatus;

public record UserJoinReqDto(
        @NotBlank(message = "이름을 필수로 입력해야 합니다.")
        @Size(min = 2, max = 15, message = "2자 이상, 15자 이하로 입력하세요.")
        String name,

        @NotBlank(message = "이메일을 필수로 입력해야 합니다.")
        @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식이 맞지 않습니다.")
        @Schema(description = "email", example = "test@gmail.com")
        String email,

        @NotBlank(message = "비밀번호를 필수로 입력해야 합니다.")
        @Size(min = 8, message = "8자 이상 입력하세요.")
        String password,

        String phone,
        String birthDay,
        String address

) {
        public User toEntity(String encodedPassword) {
                return User.builder()
                        .name(this.name)
                        .email(this.email)
                        .password(encodedPassword)
                        .phone(this.phone)
                        .birthDay(this.birthDay)
                        .address(this.address)
                        .userStatus(UserStatus.USER)
                        .build();
        }
}
