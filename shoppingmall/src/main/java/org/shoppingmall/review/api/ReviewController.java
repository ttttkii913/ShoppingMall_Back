package org.shoppingmall.review.api;

import lombok.RequiredArgsConstructor;
import org.shoppingmall.common.config.ApiResponseTemplate;
import org.shoppingmall.common.error.SuccessCode;
import org.shoppingmall.review.api.dto.request.ReviewReqDto;
import org.shoppingmall.review.api.dto.response.ReviewResDto;
import org.shoppingmall.review.application.ReviewService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 등록
    @PostMapping(value = "/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponseTemplate<ReviewResDto> reviewSave(@PathVariable("productId") Long productId,
                                                        @RequestParam(value = "reviewImage", required = false) MultipartFile reviewImage,
                                                        @RequestPart("review") ReviewReqDto reviewReqDto,
                                                        Principal principal) throws IOException {
        ReviewResDto reviewResDto = reviewService.reviewSave(productId, reviewReqDto, reviewImage, principal);
        return ApiResponseTemplate.successResponse(reviewResDto, SuccessCode.REVIEW_SAVE_SUCCESS);
    }

    // 리뷰 조회

    // 리뷰 수정

    // 리뷰 삭제

}
