package org.shoppingmall.user.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.shoppingmall.common.config.ApiResponseTemplate;
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
public class UserController {
    private final UserService userService;

    @Operation(summary = "회원가입", description = "자체 회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다.")
    })
    @PostMapping("/user/join")
    public ApiResponseTemplate<String> userSignUp(@RequestBody @Valid UserJoinReqDto userJoinReqDto) {
        userService.userSignUp(userJoinReqDto);
        return ApiResponseTemplate.successWithNoContent(SuccessCode.USER_SIGNUP_SUCCESS);
    }

    @Operation(summary = "로그인", description = "자체 로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다.")
    })
    @PostMapping("/user/login")
    private ApiResponseTemplate<UserLoginResDto> userSignIn(@RequestBody @Valid UserLoginReqDto userLoginReqDto) {
        UserLoginResDto userLoginResDto = userService.userSignIn(userLoginReqDto);
        return ApiResponseTemplate.successResponse(userLoginResDto, SuccessCode.USER_LOGIN_SUCCESS);
    }

    @Operation(summary = "사용자 정보 변경", description = "사용자 정보 변경(이름, 이메일, 비밀번호, 생일, 주소)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다.")
    })
    @PostMapping("/myPage/info/change")
    public ApiResponseTemplate<UserInfoResDto> userInfoUpdate(@RequestBody @Valid UserInfoUpdateReqDto userInfoUpdateReqDto, Principal principal) {
        UserInfoResDto userInfoResDto = userService.userInfoUpdate(userInfoUpdateReqDto, principal);
        return ApiResponseTemplate.successResponse(userInfoResDto, SuccessCode.GET_SUCCESS);
    }


    @Operation(summary = "사용자 정보 조회", description = "사용자의 모든 정보를 조회합니다.(사용자마다)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다.")
    })
    @GetMapping("/myPage/view/info")
    public ApiResponseTemplate<UserInfoResDto> getUserInfo(Principal principal) {
        UserInfoResDto userInfoResDto = userService.getUserInfo(principal);
        return ApiResponseTemplate.successResponse(userInfoResDto, SuccessCode.GET_SUCCESS);
    }
}
