package org.shoppingmall.comment.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shoppingmall.comment.api.dto.request.CommentSaveReqDto;
import org.shoppingmall.comment.api.dto.request.CommentUpdateReqDto;
import org.shoppingmall.comment.api.dto.response.CommentInfoResDto;
import org.shoppingmall.comment.api.dto.response.CommentListResDto;
import org.shoppingmall.comment.domain.Comment;
import org.shoppingmall.comment.domain.repository.CommentRepository;
import org.shoppingmall.common.EntityFinderException;
import org.shoppingmall.common.error.ErrorCode;
import org.shoppingmall.common.exception.CustomException;
import org.shoppingmall.review.domain.Review;
import org.shoppingmall.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final EntityFinderException entityFinder;

    // 댓글 전체 조회
    public CommentListResDto getCommentList(Long reviewId, Principal principal) {
        User user = entityFinder.getUserFromPrincipal(principal);
        entityFinder.getReviewById(reviewId);

        // 댓글 전체 리스트 조회
        List<Comment> parentComments = commentRepository.findParentCommentsWithChildComments(reviewId);

        // 댓글 개수 조회
        int totalCount = commentRepository.countByReview_Id(reviewId);
        return CommentListResDto.from(totalCount, parentComments, user);
    }

    // 댓글 생성
    @Transactional
    public CommentInfoResDto saveComment(Long reviewId, CommentSaveReqDto commentSaveReqDto, Principal principal) {
        User user = entityFinder.getUserFromPrincipal(principal);
        Review review = entityFinder.getReviewById(reviewId);

        Comment parentComment = null;
        // 부모 댓글에 자식 댓글을 작성하는 경우
        if (commentSaveReqDto.parentCommentId() != null) {
            // 부모 댓글의 id 설정
            parentComment = entityFinder.getCommentById(commentSaveReqDto.parentCommentId());

            // 자식 댓글의 자식 댓글 작성 방지: 부모 - 댓글 2단계로만 설정
            if (parentComment.getParentComment() != null) {
                throw new CustomException(ErrorCode.NOT_CHILD_COMMENT_HIERARCHY
                        , ErrorCode.NOT_CHILD_COMMENT_HIERARCHY.getMessage());
            }
        }

        // 댓글 생성
        Comment comment = Comment.builder()
                .content(commentSaveReqDto.content())
                .review(review)
                .user(user)
                .parentComment(parentComment)
                .build();

        Comment savedComment = commentRepository.save(comment);

        // comment count update
        review.updateCommentCount(1);

        return CommentInfoResDto.from(savedComment);
    }

    // 댓글 수정
    @Transactional
    public CommentInfoResDto updateComment(Long commentId, CommentUpdateReqDto commentUpdateReqDto, Principal principal) {
        User user = entityFinder.getUserFromPrincipal(principal);
        Comment comment = entityFinder.getCommentById(commentId);

        // 권한 확인
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.FORBIDDEN_EXCEPTION
                    , ErrorCode.FORBIDDEN_EXCEPTION.getMessage());
        }

        comment.updateComment(commentUpdateReqDto.content());
        return CommentInfoResDto.from(comment);
    }

    // 댓글 삭제
    @Transactional
    public int deleteComment(Long commentId, Principal principal) {
        User user = entityFinder.getUserFromPrincipal(principal);
        Comment comment = entityFinder.getCommentById(commentId);

        // 권한 확인
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.FORBIDDEN_EXCEPTION
                    , ErrorCode.FORBIDDEN_EXCEPTION.getMessage());
        }

        // comment count update
        comment.getReview().updateCommentCount(-1);

        commentRepository.delete(comment);

        // commentCount
        Review review = comment.getReview();
        int commentCount = commentRepository.countByReview_Id(review.getId());

        return commentCount;
    }
}
