package org.shoppingmall.order.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.shoppingmall.orderItem.domain.OrderItem;
import org.shoppingmall.payment.domain.Payment;
import org.shoppingmall.user.domain.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    private LocalDate orderDate;

    private String orderUid;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    // fk
    // 한 명의 사용자는 여러 개의 주문을 할 수 있다.
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 한 개의 주문에는 여러 개의 주문 상품이 들어있다.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Builder
    private Order(String orderUid, User user, Payment payment) {
        this.orderUid = orderUid;
        this.user = user;
        this.payment = payment;
        this.orderDate = LocalDate.now();
        this.orderStatus = OrderStatus.PENDING;
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

}
