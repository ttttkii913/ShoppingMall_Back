package org.shoppingmall.comment.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CommentUpdateReqDto(
        @NotBlank(message = "내용은 필수로 입력해야 합니다.")
        String content
) {
}
