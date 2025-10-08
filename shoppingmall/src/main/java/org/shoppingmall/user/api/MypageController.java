package org.shoppingmall.user.api;

import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "마이페이지 API", description = "MyPage 관련 API")
@CommonApiResponse
@RequestMapping("/mypage/info")
public class MypageController {
    private final UserService userService;

    @Operation(summary = "사용자 정보 변경", description = "사용자 정보 변경(이름, 이메일, 비밀번호, 생일, 주소)")
    @PatchMapping("/change")
    public ApiResponseTemplate<UserInfoResDto> userInfoUpdate(@RequestBody @Valid UserInfoUpdateReqDto userInfoUpdateReqDto, Principal principal) {
        UserInfoResDto userInfoResDto = userService.userInfoUpdate(userInfoUpdateReqDto, principal);
        return ApiResponseTemplate.successResponse(SuccessCode.GET_SUCCESS, userInfoResDto);
    }

    @Operation(summary = "사용자 정보 조회", description = "사용자의 개인 정보를 조회합니다.")
    @GetMapping("/view")
    public ApiResponseTemplate<UserInfoResDto> getUserInfo(Principal principal) {
        UserInfoResDto userInfoResDto = userService.getUserInfo(principal);
        return ApiResponseTemplate.successResponse(SuccessCode.GET_SUCCESS, userInfoResDto);
    }
}
