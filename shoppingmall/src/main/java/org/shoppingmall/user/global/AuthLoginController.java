package org.shoppingmall.user.global;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.shoppingmall.common.config.ApiResponseTemplate;
import org.shoppingmall.common.config.CommonApiResponse;
import org.shoppingmall.common.error.ErrorCode;
import org.shoppingmall.common.exception.CustomException;
import org.shoppingmall.user.domain.User;
import org.shoppingmall.user.global.oauth2.google.application.GoogleLoginService;
import org.shoppingmall.user.global.oauth2.kakao.application.KakaoLoginService;
import org.shoppingmall.user.global.oauth2.naver.application.NaverLoginService;
import org.shoppingmall.user.jwt.LoginResDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
@CommonApiResponse
public class AuthLoginController {
    
    private final AuthLoginService authLoginService;
    private final GoogleLoginService googleLoginService;
    private final KakaoLoginService kakaoLoginService;
    private final NaverLoginService naverLoginService;

    @Operation(summary = "구글 로그인", description = "구글 로그인 콜백 api입니다.")
    @GetMapping("/google")
    public ResponseEntity<ApiResponseTemplate<LoginResDto>> googleCallback(@RequestParam String code) {
        User user = googleLoginService.processLogin(code);
        return authLoginService.loginSuccess(user);
    }

    @Operation(summary = "카카오 로그인", description = "카카오 로그인 콜백 api입니다.")
    @GetMapping("/kakao")
    public ResponseEntity<ApiResponseTemplate<LoginResDto>> kakaoCallback(@RequestParam String code) {
        User user = kakaoLoginService.processLogin(code);
        return authLoginService.loginSuccess(user);
    }

    @Operation(summary = "네이버 로그인", description = "네이버 로그인 콜백 api입니다.")
    @GetMapping("/naver")
    public ResponseEntity<ApiResponseTemplate<LoginResDto>> naverCallback(@RequestParam String code, @RequestParam String state) {
        User user = naverLoginService.processLogin(code, state);
        return authLoginService.loginSuccess(user);
    }

    @Operation(summary = "리프레시 토큰으로 액세스 토큰 재발급", description = "쿠키의 리프레시 토큰으로 액세스 토큰을 재발급합니다.")
    @PostMapping("/refreshtoken")
    public ResponseEntity<ApiResponseTemplate<String>> refreshAccessToken(@CookieValue(value = "refreshToken", required = false) String refreshToken) {
        if (refreshToken == null) {
            throw new CustomException(ErrorCode.NO_AUTHORIZATION_EXCEPTION, "리프레시 토큰이 없습니다.");
        }
        return authLoginService.reissueAccessToken(refreshToken);
    }
}
