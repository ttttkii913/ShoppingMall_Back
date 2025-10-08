package org.shoppingmall.like.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.shoppingmall.common.config.ApiResponseTemplate;
import org.shoppingmall.common.config.CommonApiResponse;
import org.shoppingmall.common.error.SuccessCode;
import org.shoppingmall.like.application.LikeService;
import org.shoppingmall.like.domain.LikeType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
@Tag(name = "좋아요 API", description = "좋아요 생성, 삭제 API")
@CommonApiResponse
public class LikeController {

    private final LikeService likeService;

    @Operation(summary = "사용자가 상품에 공감", description = "인증된 사용자가 상품에 공감 표시를 합니다.")
    @PostMapping
    public ApiResponseTemplate<String> likeSave(@RequestParam Long productId,
                                                @RequestParam LikeType likeType,
                                                Principal principal) {
        likeService.likeSave(productId, likeType, principal);
        return ApiResponseTemplate.successWithNoContent(SuccessCode.LIKE_SAVE_SUCCESS);
    }

    @Operation(summary = "사용자가 상품에 공감 취소", description = "인증된 사용자가 상품에 공감 표시를 취소합니다.")
    @DeleteMapping
    public ApiResponseTemplate<String> likeDelete(@RequestParam Long productId, Principal principal) {
        likeService.likeDelete(productId, principal);
        return ApiResponseTemplate.successWithNoContent(SuccessCode.LIKE_DELETE_SUCCESS);
    }
}
