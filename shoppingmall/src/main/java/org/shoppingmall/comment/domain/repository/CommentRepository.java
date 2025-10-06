package org.shoppingmall.comment.domain.repository;

import org.shoppingmall.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c LEFT JOIN FETCH c.childComments " +
            "WHERE c.review.id = :reviewId AND c.parentComment IS NULL " +
            "ORDER BY c.commentCreatedAt ASC")
    List<Comment> findParentCommentsWithChildComments(Long reviewId);

    int countByReview_Id(Long reviewId);
}
