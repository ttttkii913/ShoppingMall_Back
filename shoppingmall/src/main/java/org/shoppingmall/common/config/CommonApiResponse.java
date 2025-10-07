package org.shoppingmall.common.config;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "응답 생성에 성공하였습니다."),
        @ApiResponse(responseCode = "500", description = "내부 서버 오류. 관리자 문의.")
})
public @interface CommonApiResponse {
    // swagger annotation 공통화
}
