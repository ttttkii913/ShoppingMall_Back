package org.shoppingmall.payment.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.shoppingmall.order.domain.Order;

@Entity
@Getter
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long price;

    private Status status;

    private String paymentUid;

    @OneToOne
    private Order order;

    @Builder
    private Payment(Long price, Status status) {
        this.price = price;
        this.status = status;
    }

    public void updateStatus(Status status, String paymentUid) {
        this.status = status;
        this.paymentUid = paymentUid;
    }
}