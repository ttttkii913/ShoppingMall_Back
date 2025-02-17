package org.shoppingmall.like.domain;

import jakarta.persistence.*;
import lombok.*;
import org.shoppingmall.product.domain.Product;
import org.shoppingmall.user.domain.User;

@Entity(name = "likes") // like 가 예약어라 likes로 엔티티 이름 지정해줘야함
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Like {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    private Integer likeCount = 0;

    // fk
    // 한 명의 사용자는 여러 개의 좋아요를 누를 수 있다.
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 하나의 상품에는 여러 개의 좋아요가 있다.
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // 좋아요 타입 추가(슬퍼요, 화나요 등등 ..)
    @Enumerated(EnumType.STRING)
    private LikeType likeType;

    public static Like of(User user, Product product, LikeType likeType) {
        return Like.builder()
                .user(user)
                .product(product)
                .likeType(likeType)
                .build();
    }
}
