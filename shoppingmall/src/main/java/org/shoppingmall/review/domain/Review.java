package org.shoppingmall.review.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
}
