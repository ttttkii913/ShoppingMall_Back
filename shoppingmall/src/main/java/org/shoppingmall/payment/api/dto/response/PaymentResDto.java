package org.shoppingmall.payment.api.dto.response;

import lombok.Builder;
import org.shoppingmall.order.domain.Order;

@Builder
public record PaymentResDto(
        String customerName,
        String customerEmail,
        String customerAddress,
        Long price,
        String orderUid
) {
    public static PaymentResDto from(Order order) {
        return PaymentResDto.builder()
                .customerName(order.getUser().getName())
                .customerEmail(order.getUser().getEmail())
                .customerAddress(order.getUser().getAddress())
                .orderUid(order.getOrderUid())
                .build();
    }
}