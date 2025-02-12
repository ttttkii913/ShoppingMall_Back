package org.shoppingmall.order.api.dto.request;

import org.shoppingmall.order.domain.Order;
import org.shoppingmall.orderItem.api.dto.request.OrderItemReqDto;
import org.shoppingmall.orderItem.domain.OrderItem;
import org.shoppingmall.payment.domain.Payment;
import org.shoppingmall.user.domain.User;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record OrderReqDto(
    Long paymentId,
    List<OrderItemReqDto> orderItems
) {
    public Order toEntity(User user, Payment payment) {
        return Order.builder()
                .orderUid(UUID.randomUUID().toString())
                .user(user)
                .payment(payment)
                .build();
    }

    public List<OrderItem> toOrderItemEntity(Order order) {
        return orderItems.stream()
                .map(orderItemReqDto -> orderItemReqDto.toEntity(order))
                .collect(Collectors.toList());
    }

}
