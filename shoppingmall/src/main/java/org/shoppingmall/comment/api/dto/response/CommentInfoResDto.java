package org.shoppingmall.comment.api.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.shoppingmall.comment.domain.Comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

// 대댓글 포함 댓글 상세 리스트
public record CommentInfoResDto(
        boolean isParent,
        Long commentId,
        Long communityId,
        String content,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime commentCreatedAt,
        String writerNickname,
        Long commentCount,
        List<CommentInfoResDto> childComments
) {
    public static CommentInfoResDto from(Comment comment) {
        List<CommentInfoResDto> childComments = comment.getChildComments().stream()
                .map(CommentInfoResDto::from)
                .collect(Collectors.toList());

        boolean isParent = comment.getParentComment() == null;

        return new CommentInfoResDto(
                isParent,
                comment.getId(),
                comment.getReview().getId(),
                comment.getContent(),
                comment.getCommentCreatedAt(),
                comment.getUser().getName(),
                comment.getReview().getCommentCount(),
                childComments
        );
    }
}
