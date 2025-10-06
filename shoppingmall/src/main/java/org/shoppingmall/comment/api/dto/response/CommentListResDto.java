package org.shoppingmall.comment.api.dto.response;


import org.shoppingmall.comment.domain.Comment;
import org.shoppingmall.user.domain.User;

import java.util.List;
import java.util.stream.Collectors;

// 부모 댓글 리스트 + 자식 댓글 리스트
public record CommentListResDto(
        int totalCommentCount,
        List<CommentInfoResDto> commentInfoResDtos
) {
    public static CommentListResDto from(int totalCommentCount, List<Comment> comments, User user) {
        List<CommentInfoResDto> commentInfoResDtoList = comments.stream()
                .map(CommentInfoResDto::from)
                .collect(Collectors.toList());

        return new CommentListResDto(totalCommentCount, commentInfoResDtoList);
    }
}
