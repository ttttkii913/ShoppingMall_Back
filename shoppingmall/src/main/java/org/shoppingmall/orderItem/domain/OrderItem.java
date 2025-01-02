package org.shoppingmall.orderItem.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.shoppingmall.order.domain.Order;
import org.shoppingmall.product.domain.Product;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderItem_id")
    private Long id;

    private Integer totalPrice;
    private Integer totalCount;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    // fk
    // 하나의 주문에는 여러 개의 주문 상품이 있다.
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    // 여러 개의 주문 상품에는 하나의 상품이 있다.
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
