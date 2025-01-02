package org.shoppingmall.orderItem.domain.repository;

import org.shoppingmall.orderItem.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
