package org.shoppingmall.user.global;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shoppingmall.common.config.ApiResponseTemplate;
import org.shoppingmall.common.error.ErrorCode;
import org.shoppingmall.common.error.SuccessCode;
import org.shoppingmall.common.exception.CustomException;
import org.shoppingmall.user.domain.User;
import org.shoppingmall.user.domain.repository.UserRepository;
import org.shoppingmall.user.jwt.JwtTokenProvider;
import org.shoppingmall.user.jwt.LoginResDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthLoginService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Value("${cookie.secure}")
    private boolean cookieSecure;

    @Value("${cookie.sameSite}")
    private String cookieSameSite;

    // refreshToken 저장
    public ResponseEntity<ApiResponseTemplate<LoginResDto>> loginSuccess(User user) {
        String accessToken = jwtTokenProvider.generateToken(user);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user);

        // DB에 리프레시 토큰 저장
        user.saveRefreshToken(refreshToken);
        userRepository.save(user);

        // HttpOnly 쿠키로 리프레시 토큰 전달
        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(cookieSecure)
                .path("/")
                .maxAge(Long.parseLong(jwtTokenProvider.getRefreshTokenExpireTime()) / 1000)
                .sameSite(cookieSameSite)
                .build();

        System.out.println("cookieSecure = " + cookieSecure);
        System.out.println("cookieSameSite = " + cookieSameSite);

        LoginResDto loginResDto = new LoginResDto(accessToken, user.getId(), user.getUserStatus());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(ApiResponseTemplate.successResponse(SuccessCode.USER_LOGIN_SUCCESS, loginResDto));
    }

    // refreshToken으로 새로운 accessToken 생성
    public ResponseEntity<ApiResponseTemplate<String>> reissueAccessToken(String refreshToken) {
        User user = userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_AUTHORIZATION_EXCEPTION
                        , ErrorCode.NO_AUTHORIZATION_EXCEPTION.getMessage()));

        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new CustomException(ErrorCode.NO_AUTHORIZATION_EXCEPTION, "리프레시 토큰이 만료되었습니다.");
        }

        String newAccessToken = jwtTokenProvider.generateToken(user);
        return ResponseEntity.ok(ApiResponseTemplate.successResponse(SuccessCode.REFRESH_TOKEN_SUCCESS, newAccessToken));
    }
}
