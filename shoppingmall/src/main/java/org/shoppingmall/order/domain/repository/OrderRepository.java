package org.shoppingmall.order.domain.repository;

import org.shoppingmall.order.domain.Order;
import org.shoppingmall.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o" +
            " LEFT JOIN FETCH o.payment p" +
            " LEFT JOIN FETCH o.user u" +
            " WHERE o.orderUid = :orderUid")
    Optional<Order> findOrderAndPaymentAndUser(String orderUid);

    @Query("SELECT o FROM Order o" +
            " LEFT JOIN FETCH o.payment p" +
            " WHERE o.orderUid = :orderUid")
    Optional<Order> findOrderAndPayment(String orderUid);


    List<Order> findAllByUser(User user);

}
