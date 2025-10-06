package org.shoppingmall.common.config;

import lombok.*;
import org.shoppingmall.common.error.ErrorCode;
import org.shoppingmall.common.error.SuccessCode;

@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ApiResponseTemplate<T> {

    private final int code;       // 응답 코드 (200, 404 등)
    private final String message; // 응답 메시지
    private T data;               // 응답 데이터

    // 데이터 없는 성공 응답
    public static ApiResponseTemplate successWithNoContent(SuccessCode successCode) {
        return new ApiResponseTemplate<>(successCode.getHttpStatusCode(), successCode.getMessage());
    }

    // 데이터 포함한 성공 응답
    public static <T> ApiResponseTemplate<T> successResponse(SuccessCode successCode, T data) {
        return new ApiResponseTemplate<>(successCode.getHttpStatusCode(), successCode.getMessage(), data);
    }

    // 에러 응답 (커스텀 메시지 포함)
    public static ApiResponseTemplate errorResponse(ErrorCode errorCode, String customMessage) {
        return new ApiResponseTemplate<>(errorCode.getHttpStatusCode(), customMessage);
    }

}
