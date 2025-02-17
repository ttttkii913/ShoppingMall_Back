package org.shoppingmall.like.api;

import lombok.RequiredArgsConstructor;
import org.shoppingmall.common.config.ApiResponseTemplate;
import org.shoppingmall.common.error.SuccessCode;
import org.shoppingmall.like.application.LikeService;
import org.shoppingmall.like.domain.LikeType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {

    private final LikeService likeService;

    // 좋아요 등록
    @PostMapping("/{productId}")
    public ApiResponseTemplate<String> likeSave(@PathVariable("productId") Long productId,
                                                @RequestParam LikeType likeType,
                                                Principal principal) {
        likeService.likeSave(productId, likeType, principal);
        return ApiResponseTemplate.successWithNoContent(SuccessCode.LIKE_SAVE_SUCCESS);
    }

    // 좋아요 취소
    @DeleteMapping("/{productId}")
    public ApiResponseTemplate<String> likeDelete(@PathVariable("productId") Long productId, Principal principal) {
        likeService.likeDelete(productId, principal);
        return ApiResponseTemplate.successWithNoContent(SuccessCode.LIKE_DELETE_SUCCESS);
    }

}
