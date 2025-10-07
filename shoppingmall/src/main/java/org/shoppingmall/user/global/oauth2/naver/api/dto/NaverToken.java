package org.shoppingmall.user.global.oauth2.naver.api.dto;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class NaverToken {

    @SerializedName("access_token")
    private String accessToken;

}
