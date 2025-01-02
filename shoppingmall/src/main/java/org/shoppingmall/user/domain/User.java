package org.shoppingmall.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.shoppingmall.cart.domain.Cart;
import org.shoppingmall.review.domain.Review;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    private String name;

    private String password;
    private String email;
    private String phone;
    private String birthDay;
    private String address;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    private String accessToken;
    private String refreshToken;

    // fk
    // 한 명의 사용자는 여러 개의 리뷰를 쓸 수 있다.
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    // 한 명의 사용자는 한 개의 장바구니를 가질 수 있다.
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Builder
    private User(String name, String password, String email, String phone, String birthDay, String address, UserStatus userStatus, String accessToken, String refreshToken) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.birthDay = birthDay;
        this.address = address;
        this.userStatus = userStatus;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
