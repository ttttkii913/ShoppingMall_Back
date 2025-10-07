package org.shoppingmall.user.global.oauth2.naver.api.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class NaverUserInfo {

    private String resultcode;
    private String message;
    private Response response;

    @Data
    public static class Response {
        private String id;
        private String nickname;
        private String name;
        private String email;

        @SerializedName("profile_image")
        private String pictureUrl;
    }

}
