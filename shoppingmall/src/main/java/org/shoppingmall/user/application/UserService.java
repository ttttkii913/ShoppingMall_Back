package org.shoppingmall.user.application;

import lombok.RequiredArgsConstructor;
import org.shoppingmall.common.EntityFinder;
import org.shoppingmall.common.error.ErrorCode;
import org.shoppingmall.user.api.dto.request.UserInfoUpdateReqDto;
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

import java.security.Principal;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider JwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    // 자체 회원가입
    @Transactional
    public void userSignUp(UserJoinReqDto userJoinReqDto) {
        if (userRepository.existsByEmail(userJoinReqDto.email())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(userJoinReqDto.password());
        String accessToken = JwtTokenProvider.generateToken(userJoinReqDto.email());
        String refreshToken = JwtTokenProvider.refreshToken(userJoinReqDto.email());

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
    public UserInfoResDto getUserInfo(Principal principal) {
        Long id = Long.parseLong(principal.getName());
        User user = getUserById(id);
        return UserInfoResDto.from(user);
    }

    // 사용자 정보 수정
    public UserInfoResDto userInfoUpdate(UserInfoUpdateReqDto userInfoUpdateReqDto, Principal principal) {
        Long id = Long.parseLong(principal.getName());
        User user = getUserById(id);

        user.update(userInfoUpdateReqDto);

        // 비밀번호 수정할 경우에도 암호화 설정이 들어가도록(없으면 user 테이블에서 비밀번호가 그대로 보임)
        if (userInfoUpdateReqDto.password() != null && !userInfoUpdateReqDto.password().isEmpty()) {
            String newEncodedPassword = passwordEncoder.encode(userInfoUpdateReqDto.password());
            user.setPassword(newEncodedPassword);
        }

        userRepository.save(user);
        return UserInfoResDto.from(user);
    }

    // entity - user 찾기
    private User getUserById(Long id) {
        return EntityFinder.findByIdOrThrow(userRepository.findById(id)
                , ErrorCode.USER_NOT_FOUND_EXCEPTION);
    }
}
