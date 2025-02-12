package org.shoppingmall.orderItem.api.dto.request;

import org.shoppingmall.order.domain.Order;
import org.shoppingmall.orderItem.domain.DeliveryStatus;
import org.shoppingmall.orderItem.domain.OrderItem;

public record OrderItemReqDto(
        Integer totalPrice,
        Integer totalCount,
        DeliveryStatus deliveryStatus
) {
    public OrderItem toEntity(Order order) {
        return OrderItem.builder()
                .order(order)
                .totalPrice(totalPrice)
                .totalCount(totalCount)
                .deliveryStatus(deliveryStatus)
                .build();
    }
}
