package org.shoppingmall.like.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "좋아요 API", description = "좋아요 생성, 삭제 API")
public class LikeController {

    private final LikeService likeService;

    @Operation(summary = "사용자가 상품에 공감", description = "인증된 사용자가 상품에 공감 표시를 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류. 관리자 문의.")
    })
    @PostMapping("/{productId}")
    public ApiResponseTemplate<String> likeSave(@PathVariable("productId") Long productId,
                                                @RequestParam LikeType likeType,
                                                Principal principal) {
        likeService.likeSave(productId, likeType, principal);
        return ApiResponseTemplate.successWithNoContent(SuccessCode.LIKE_SAVE_SUCCESS);
    }

    @Operation(summary = "사용자가 상품에 공감 취소", description = "인증된 사용자가 상품에 공감 표시를 취소합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류. 관리자 문의.")
    })
    @DeleteMapping("/{productId}")
    public ApiResponseTemplate<String> likeDelete(@PathVariable("productId") Long productId, Principal principal) {
        likeService.likeDelete(productId, principal);
        return ApiResponseTemplate.successWithNoContent(SuccessCode.LIKE_DELETE_SUCCESS);
    }
}
