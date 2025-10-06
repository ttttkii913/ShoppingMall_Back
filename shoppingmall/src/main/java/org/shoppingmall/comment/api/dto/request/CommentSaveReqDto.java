package org.shoppingmall.comment.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CommentSaveReqDto(
        @NotBlank(message = "내용은 필수로 입력해야 합니다.")
        String content,

        @Schema(description = "부모 댓글 id, null로 보내면 부모 댓글로 간주", nullable = true, example = "null")
        Long parentCommentId
) {
}
