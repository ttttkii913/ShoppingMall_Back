package org.shoppingmall.user.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.shoppingmall.common.config.ApiResponseTemplate;
import org.shoppingmall.common.config.CommonApiResponse;
import org.shoppingmall.common.error.SuccessCode;
import org.shoppingmall.user.api.dto.request.*;
import org.shoppingmall.user.api.dto.response.UserInfoResDto;
import org.shoppingmall.user.api.dto.response.UserLoginResDto;
import org.shoppingmall.user.application.UserService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Tag(name = "로그인 API", description = "로그인 관련 API")
@CommonApiResponse
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Operation(summary = "회원가입", description = "자체 회원가입")
    @PostMapping("/join")
    public ApiResponseTemplate<String> userSignUp(@RequestBody @Valid UserJoinReqDto userJoinReqDto) {
        userService.userSignUp(userJoinReqDto);
        return ApiResponseTemplate.successWithNoContent(SuccessCode.USER_SIGNUP_SUCCESS);
    }

    @Operation(summary = "로그인", description = "자체 로그인")
    @PostMapping("/login")
    private ApiResponseTemplate<UserLoginResDto> userSignIn(@RequestBody @Valid UserLoginReqDto userLoginReqDto) {
        UserLoginResDto userLoginResDto = userService.userSignIn(userLoginReqDto);
        return ApiResponseTemplate.successResponse(SuccessCode.USER_LOGIN_SUCCESS, userLoginResDto);
    }

    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴 - soft delete")
    @DeleteMapping("/delete")
    private ApiResponseTemplate<String> userInfoDelete(Principal principal) {
        userService.userInfoDelete(principal);
        return ApiResponseTemplate.successWithNoContent(SuccessCode.MEMBER_INFO_DELETE);
    }

    @Operation(summary = "이메일 찾기", description = "사용자의 이름으로 가입한 이메일 정보를 찾습니다.")
    @PostMapping("/email")
    private ApiResponseTemplate<UserInfoResDto> userEmailInfo(FindEmailReqDto findEmailReqDto) {
        UserInfoResDto userInfoResDto = userService.userEmailInfo(findEmailReqDto);
        return ApiResponseTemplate.successResponse(SuccessCode.USER_EMAIL_FIND_SUCCESS, userInfoResDto);
    }

    @Operation(summary = "비밀번호 재설정", description = "사용자의 이메일로 인증한 후 비밀번호를 새로 변경합니다. + 비밀번호 확인 필드")
    @PostMapping("/reset/password")
    private ApiResponseTemplate<UserInfoResDto> resetPassword(ResetPasswordReqDto resetPasswordReqDto) {
        UserInfoResDto userInfoResDto = userService.resetPassword(resetPasswordReqDto);
        return ApiResponseTemplate.successResponse(SuccessCode.USER_PASSWORD_RESET_SUCCESS, userInfoResDto);
    }
}
