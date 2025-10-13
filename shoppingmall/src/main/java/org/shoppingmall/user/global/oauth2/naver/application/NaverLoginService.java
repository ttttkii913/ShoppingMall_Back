package org.shoppingmall.user.global.oauth2.naver.application;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.shoppingmall.user.domain.AuthProvider;
import org.shoppingmall.user.domain.User;
import org.shoppingmall.user.domain.UserStatus;
import org.shoppingmall.user.domain.repository.UserRepository;
import org.shoppingmall.user.global.oauth2.naver.api.dto.NaverToken;
import org.shoppingmall.user.global.oauth2.naver.api.dto.NaverUserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class NaverLoginService {

    @Value("${naver.client-id}")
    private String NAVER_CLIENT_ID;

    @Value("${naver.client-secret}")
    private String NAVER_CLIENT_SECRET;

    @Value("${naver.redirect-uri}")
    private String NAVER_REDIRECT_URI;

    private final UserRepository userRepository;

    private static final String NAVER_TOKEN_URL = "https://nid.naver.com/oauth2.0/token";

    public String getNaverAccessToken(String code, String state) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", NAVER_CLIENT_ID);
        params.add("redirect_uri", NAVER_REDIRECT_URI);
        params.add("client_secret", NAVER_CLIENT_SECRET);
        params.add("code", code);
        params.add("state", state);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(NAVER_TOKEN_URL, request, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            String json = response.getBody();
            Gson gson = new Gson();
            NaverToken tokenResponse = gson.fromJson(json, NaverToken.class);
            return tokenResponse.getAccessToken();
        } else {
            throw new RuntimeException("네이버 액세스 토큰 발급에 실패하였습니다." + response.getStatusCode());
        }
    }

    // 네이버 사용자 정보를 가져오는 메소드
    public NaverUserInfo getUserInfoFromNaver(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://openapi.naver.com/v1/nid/me";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String json = responseEntity.getBody();
            Gson gson = new Gson();
            return gson.fromJson(json, NaverUserInfo.class);
        } else {
            throw new RuntimeException("네이버 사용자 정보 조회에 실패하였습니다." + responseEntity.getStatusCode()
                    + ", Response: " + responseEntity.getBody());
        }
    }

    // 네이버 로그인 후 JWT 토큰을 반환하는 메소드
    public User processLogin(String code, String state, UserStatus userStatus) {
        String accessToken = getNaverAccessToken(code, state);
        NaverUserInfo naverUserInfo = getUserInfoFromNaver(accessToken);

        if (naverUserInfo.getResponse().getEmail() == null || naverUserInfo.getResponse().getId() == null) {
            throw new RuntimeException("네이버 회원가입 정보가 없습니다.");
        }

        return userRepository.findByEmail(naverUserInfo.getResponse().getEmail()).orElseGet(() ->
                userRepository.save(User.builder()
                        .email(naverUserInfo.getResponse().getEmail())
                        .name(naverUserInfo.getResponse().getNickname())
                        .pictureUrl(naverUserInfo.getResponse().getPictureUrl())
                        .userStatus(userStatus == UserStatus.ROLE_SELLER ? UserStatus.ROLE_SELLER : UserStatus.ROLE_USER)
                        .authProvider(AuthProvider.NAVER)
                        .build())
        );
    }
}
