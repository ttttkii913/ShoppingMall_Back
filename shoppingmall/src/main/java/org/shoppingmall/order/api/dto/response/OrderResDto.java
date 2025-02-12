package org.shoppingmall.order.api.dto.response;

import org.shoppingmall.order.domain.Order;
import org.shoppingmall.order.domain.OrderStatus;
import org.shoppingmall.orderItem.api.dto.response.OrderItemResDto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public record OrderResDto(

        Long id,
        String orderUid,
        LocalDate orderDate,
        OrderStatus orderStatus,
        List<OrderItemResDto> orderItems
) {
    public static OrderResDto from(Order order) {
        return new OrderResDto(
                order.getId(),
                order.getOrderUid(),
                order.getOrderDate(),
                order.getOrderStatus(),
                order.getOrderItems().stream()
                        .map(OrderItemResDto::from)
                        .collect(Collectors.toList())
        );
    }
}
