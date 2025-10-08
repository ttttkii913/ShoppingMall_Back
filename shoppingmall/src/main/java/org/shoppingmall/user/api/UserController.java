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
import org.shoppingmall.user.api.dto.request.UserInfoUpdateReqDto;
import org.shoppingmall.user.api.dto.request.UserJoinReqDto;
import org.shoppingmall.user.api.dto.request.UserLoginReqDto;
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
}
