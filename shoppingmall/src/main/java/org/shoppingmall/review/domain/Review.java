package org.shoppingmall.review.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.shoppingmall.product.domain.Product;
import org.shoppingmall.review.api.dto.request.ReviewUpdateReqDto;
import org.shoppingmall.user.domain.User;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    private String title;
    private String content;
    private String reviewImage;

    @Column(name = "review_createdAt")
    private LocalDate createdAt;

    // 한 명의 사용자가 여러 개의 리뷰를 쓸 수 있다.
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 상품에는 여러 개의 리뷰가 달릴 수 있다.
    @ManyToOne
    @JoinColumn(name ="product_id")
    private Product product;

    @Column(name = "comment_count", nullable = false)
    private Long commentCount;

    @Builder
    public Review(String title, String content, String reviewImage, LocalDate createdAt, User user, Product product) {
        this.title = title;
        this.content = content;
        this.reviewImage = reviewImage;
        this.createdAt = LocalDate.now();
        this.user = user;
        this.product = product;
    }

    public void update(ReviewUpdateReqDto reviewUpdateReqDto) {
        this.title = reviewUpdateReqDto.title();
        this.content = reviewUpdateReqDto.content();
    }

    public void updateImage(String reviewImage) {
        this.reviewImage = reviewImage;
    }

    public void updateCommentCount(int count) {
        this.commentCount = Math.max(0, this.commentCount + count);
    }

}
