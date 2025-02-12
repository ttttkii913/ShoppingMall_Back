package org.shoppingmall.payment.api.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PaymentCallbackReqDto(
        String paymentUid, // 결제 고유 번호
        String orderUid // 주문 고유 번호
) {
}