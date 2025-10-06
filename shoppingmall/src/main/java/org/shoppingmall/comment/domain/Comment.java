package org.shoppingmall.comment.domain;


import jakarta.persistence.*;
import lombok.*;
import org.shoppingmall.review.domain.Review;
import org.shoppingmall.user.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "comment_content")
    private String content;

    @Column(name = "comment_created_at")
    private LocalDateTime commentCreatedAt;

    /*
    부모 댓글 - 하나의 부모 댓글에는 여러 개의 자식 댓글이 달릴 수 있다.
    NULL일 경우 자식 댓글 X
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    /*
    자식 댓글 리스트 - 여러 개의 자식 댓글이 하나의 부모 댓글에 달릴 수 있다.
     */
    @OneToMany(mappedBy = "parentComment", orphanRemoval = true)
    private List<Comment> childComments = new ArrayList<>();

    // 한 명의 사용자는 여러 개의 댓글을 작성할 수 있다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 한 개의 게시글에는 여러 개의 댓글이 달릴 수 있다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Builder
    public Comment(String content, Review review, User user, Comment parentComment) {
        this.content = content;
        this.review = review;
        this.user = user;
        this.parentComment = parentComment;
        this.commentCreatedAt = LocalDateTime.now();
    }

    public void updateComment(String content) {
        this.content = content;
    }
}
