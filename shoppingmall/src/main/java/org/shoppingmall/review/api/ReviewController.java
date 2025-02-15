package org.shoppingmall.review.api;

import lombok.RequiredArgsConstructor;
import org.shoppingmall.common.config.ApiResponseTemplate;
import org.shoppingmall.common.error.SuccessCode;
import org.shoppingmall.review.api.dto.request.ReviewReqDto;
import org.shoppingmall.review.api.dto.request.ReviewUpdateReqDto;
import org.shoppingmall.review.api.dto.response.ReviewListResDto;
import org.shoppingmall.review.api.dto.response.ReviewResDto;
import org.shoppingmall.review.application.ReviewService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import retrofit2.http.Path;

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

    // 전체 리뷰 조회 - 모든 사용자
    @GetMapping
    public ApiResponseTemplate<ReviewListResDto> reviewFindAll() {
        ReviewListResDto reviewListResDto = reviewService.reviewFindAll();
        return ApiResponseTemplate.successResponse(reviewListResDto, SuccessCode.GET_SUCCESS);
    }

    // 사용자의 모든 리뷰 조회 - 인증된 사용자
    @GetMapping("/user")
    public ApiResponseTemplate<ReviewListResDto> reviewFindUser(Principal principal) {
        ReviewListResDto reviewListResDto = reviewService.reviewFindUser(principal);
        return ApiResponseTemplate.successResponse(reviewListResDto, SuccessCode.GET_SUCCESS);
    }

    // 리뷰 수정
    @PutMapping(value = "/{reviewId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponseTemplate<ReviewResDto> reviewUpdate(@PathVariable("reviewId") Long reviewId,
                                                          @RequestPart ReviewUpdateReqDto reviewUpdateReqDto,
                                                          @RequestParam(value = "reviewImage", required = false) MultipartFile reviewImage,
                                                          Principal principal) throws IOException {
        ReviewResDto reviewResDto = reviewService.reviewUpdate(reviewId, reviewUpdateReqDto, reviewImage, principal);
        return ApiResponseTemplate.successResponse(reviewResDto, SuccessCode.REVIEW_UPDATE_SUCCESS);
    }

    // 리뷰 삭제
    @DeleteMapping("/{reviewId}")
    public ApiResponseTemplate<String> reviewDelete(@PathVariable("reviewId") Long reviewId,  Principal principal) {
        reviewService.reviewDelete(reviewId, principal);
        return ApiResponseTemplate.successWithNoContent(SuccessCode.REVIEW_DELETE_SUCCESS);
    }

}
