package org.shoppingmall.user.application;

import lombok.RequiredArgsConstructor;
import org.shoppingmall.user.api.dto.request.UserJoinReqDto;
import org.shoppingmall.user.api.dto.request.UserLoginReqDto;
import org.shoppingmall.user.api.dto.response.UserInfoResDto;
import org.shoppingmall.user.api.dto.response.UserLoginResDto;
import org.shoppingmall.user.domain.User;
import org.shoppingmall.user.domain.repository.UserRepository;
import org.shoppingmall.user.jwt.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider JwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void userSignUp(UserJoinReqDto userJoinReqDto) {
        if (userRepository.existsByEmail(userJoinReqDto.email())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(userJoinReqDto.password());
        String accessToken = JwtTokenProvider.generateToken(userJoinReqDto.email());
        String refreshToken = JwtTokenProvider.refreshToken(userJoinReqDto.email());

        // 정적 메서드 활용
        User user = userJoinReqDto.toEntity(encodedPassword, accessToken, refreshToken);

        userRepository.save(user);
    }

    // 로그인
    public UserLoginResDto userSignIn(UserLoginReqDto userLoginReqDto) {
        User user = userRepository.findByEmail(userLoginReqDto.email())
                .orElseThrow(() -> new IllegalArgumentException("이메일이나 패스워드가 일치하지 않습니다."));

        if (!passwordEncoder.matches(userLoginReqDto.password(), user.getPassword())) {
            throw new IllegalArgumentException("이메일이나 패스워드가 일치하지 않습니다.");
        }

        String accessToken = JwtTokenProvider.generateToken(user.getEmail());
        String refreshToken = JwtTokenProvider.refreshToken(user.getEmail());

        return UserLoginResDto.of(user, accessToken, refreshToken);
    }

    // 사용자 정보 조회
    public UserInfoResDto getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // UserInfoResDto.from 메서드 활용
        return UserInfoResDto.from(user, user.getAccessToken(), user.getRefreshToken());
    }
}
