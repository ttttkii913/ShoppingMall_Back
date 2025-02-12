package org.shoppingmall.orderItem.api.dto.response;

import org.shoppingmall.orderItem.domain.OrderItem;
import org.shoppingmall.orderItem.domain.DeliveryStatus;

public record OrderItemResDto(
        Integer totalPrice,
        Integer totalCount,
        DeliveryStatus deliveryStatus
) {
    public static OrderItemResDto from(OrderItem orderItem) {
        return new OrderItemResDto(
                orderItem.getTotalPrice(),
                orderItem.getTotalCount(),
                orderItem.getDeliveryStatus()
        );
    }
}
