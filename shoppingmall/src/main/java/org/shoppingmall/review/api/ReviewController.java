package org.shoppingmall.review.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.shoppingmall.common.config.ApiResponseTemplate;
import org.shoppingmall.common.config.CommonApiResponse;
import org.shoppingmall.common.error.SuccessCode;
import org.shoppingmall.review.api.dto.request.ReviewReqDto;
import org.shoppingmall.review.api.dto.request.ReviewUpdateReqDto;
import org.shoppingmall.review.api.dto.response.ReviewListResDto;
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
@Tag(name = "리뷰 API", description = "리뷰 CRUD API")
@CommonApiResponse
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "상품에 리뷰 생성", description = "인증된 사용자가 상품에 리뷰를 생성합니다.")
    @PostMapping(value = "/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponseTemplate<ReviewResDto> reviewSave(@PathVariable("productId") Long productId,
                                                        @RequestParam(value = "reviewImage", required = false) MultipartFile reviewImage,
                                                        @RequestPart("review") ReviewReqDto reviewReqDto,
                                                        Principal principal) throws IOException {
        ReviewResDto reviewResDto = reviewService.reviewSave(productId, reviewReqDto, reviewImage, principal);
        return ApiResponseTemplate.successResponse(SuccessCode.REVIEW_SAVE_SUCCESS, reviewResDto);
    }

    @Operation(summary = "모든 리뷰 리스트 조회", description = "모든 사용자가 모든 리뷰 리스트를 조회합니다.")
    @GetMapping("/all")
    public ApiResponseTemplate<ReviewListResDto> reviewFindAll() {
        ReviewListResDto reviewListResDto = reviewService.reviewFindAll();
        return ApiResponseTemplate.successResponse(SuccessCode.GET_SUCCESS, reviewListResDto);
    }

    @Operation(summary = "사용자만의 리뷰 리스트 조회", description = "인증된 사용자가 자신의 모든 리뷰 리스트를 조회합니다.")
    @GetMapping("/user")
    public ApiResponseTemplate<ReviewListResDto> reviewFindUser(Principal principal) {
        ReviewListResDto reviewListResDto = reviewService.reviewFindUser(principal);
        return ApiResponseTemplate.successResponse(SuccessCode.GET_SUCCESS, reviewListResDto);
    }

    @Operation(summary = "상품에 리뷰 수정", description = "인증된 사용자가 상품에 리뷰를 수정합니다.")
    @PutMapping(value = "/{reviewId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponseTemplate<ReviewResDto> reviewUpdate(@PathVariable("reviewId") Long reviewId,
                                                          @RequestPart ReviewUpdateReqDto reviewUpdateReqDto,
                                                          @RequestParam(value = "reviewImage", required = false) MultipartFile reviewImage,
                                                          Principal principal) throws IOException {
        ReviewResDto reviewResDto = reviewService.reviewUpdate(reviewId, reviewUpdateReqDto, reviewImage, principal);
        return ApiResponseTemplate.successResponse(SuccessCode.REVIEW_UPDATE_SUCCESS, reviewResDto);
    }

    @Operation(summary = "상품에 리뷰 삭제", description = "인증된 사용자가 상품에 리뷰를 삭제합니다.")
    @DeleteMapping("/{reviewId}")
    public ApiResponseTemplate<String> reviewDelete(@PathVariable("reviewId") Long reviewId,  Principal principal) {
        reviewService.reviewDelete(reviewId, principal);
        return ApiResponseTemplate.successWithNoContent(SuccessCode.REVIEW_DELETE_SUCCESS);
    }

}
