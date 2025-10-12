package org.shoppingmall.seller.domain;

import jakarta.persistence.*;
import lombok.*;
import org.shoppingmall.user.domain.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Seller {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seller_id")
    private Long id;

    private String businessName;
    private String businessNumber;
    private String businessEmail;
    private String contactNumber;
    private String bankName;
    private String accountNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
