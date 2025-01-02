package org.shoppingmall.order.domain.repository;

import org.shoppingmall.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
