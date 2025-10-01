package org.shoppingmall.review.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shoppingmall.common.EntityFinderException;
import org.shoppingmall.common.config.S3Service;
import org.shoppingmall.common.error.ErrorCode;
import org.shoppingmall.common.exception.NotFoundException;
import org.shoppingmall.order.domain.repository.OrderRepository;
import org.shoppingmall.product.domain.Product;
import org.shoppingmall.product.domain.repository.ProductRepository;
import org.shoppingmall.review.api.dto.request.ReviewReqDto;
import org.shoppingmall.review.api.dto.request.ReviewUpdateReqDto;
import org.shoppingmall.review.api.dto.response.ReviewListResDto;
import org.shoppingmall.review.api.dto.response.ReviewResDto;
import org.shoppingmall.review.domain.Review;
import org.shoppingmall.review.domain.repository.ReviewRepository;
import org.shoppingmall.user.domain.User;
import org.shoppingmall.user.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final S3Service s3Service;
    private final EntityFinderException entityFinder;

    // 리뷰 등록
    public ReviewResDto reviewSave(Long productId, ReviewReqDto reviewReqDto
                                    , MultipartFile multipartFile, Principal principal) throws IOException {
        // 사용자 찾기
        User user = entityFinder.getUserFromPrincipal(principal);

        // 상품 찾기
        Product product = entityFinder.getProductById(productId);

        // 이미지 업로드를 선택적으로 넘기면서 이미지가 있을 경우에만 업로드
        String reviewImage = null; // 이미지 URL을 저장할 변수
        if (multipartFile != null && !multipartFile.isEmpty()) {
            reviewImage = s3Service.upload(multipartFile, "review");
        }

        // 주문한 상품인지 확인
        /*boolean hasOrdered = orderRepository.existsByUserAndProduct(user, product);
        if (!hasOrdered) {
            throw new IllegalArgumentException("구매한 상품에만 리뷰를 작성할 수 있습니다.");
        }*/

        // 리뷰 저장
        Review review = reviewReqDto.toEntity(reviewImage, user, product);
        reviewRepository.save(review);

        // 리뷰 res dto 리턴
        return ReviewResDto.from(review);
    }

    // 모든 리뷰 조회
    public ReviewListResDto reviewFindAll() {
        List<Review> reviews = reviewRepository.findAll();
        List<ReviewResDto> reviewResDtos = reviews.stream()
                .map(ReviewResDto::from)
                .toList();
        return ReviewListResDto.from(reviewResDtos);
    }

    // 사용자 리뷰 조회
    public ReviewListResDto reviewFindUser(Principal principal) {
        Long id = Long.parseLong(principal.getName());
        List<Review> reviews = reviewRepository.findByUserId(id).orElseThrow(
                ()-> new NotFoundException(ErrorCode.REVIEW_NOT_FOUND_EXCEPTION
                        , ErrorCode.REVIEW_NOT_FOUND_EXCEPTION.getMessage()));

        List<ReviewResDto> reviewResDtos = reviews.stream()
                .map(ReviewResDto::from)
                .toList();
        return ReviewListResDto.from(reviewResDtos);

    }

    // 리뷰 수정
    public ReviewResDto reviewUpdate(Long reviewId, ReviewUpdateReqDto reviewUpdateReqDto, MultipartFile reviewImage, Principal principal) throws IOException {

        Review review = entityFinder.getReviewById(reviewId);
        Long id = Long.parseLong(principal.getName());
        Long currentUser = review.getUser().getId();

        if (!id.equals(currentUser)) {
            throw new NotFoundException(ErrorCode.NO_AUTHORIZATION_EXCEPTION
                    , ErrorCode.NO_AUTHORIZATION_EXCEPTION.getMessage());
        }
        review.update(reviewUpdateReqDto);

        // 이미지가 있을 경우 S3에 업로드하고 URL 업데이트
        if (reviewImage != null && !reviewImage.isEmpty()) {
            String imageUrl = s3Service.upload(reviewImage, "review");
            review.updateImage(imageUrl);
        }

        reviewRepository.save(review);

        return ReviewResDto.from(review);
    }

    // 리뷰 삭제
    public void reviewDelete(Long reviewId, Principal principal) {
        Long id = Long.parseLong(principal.getName());
        Review review = entityFinder.getReviewById(reviewId);

        Long currentUser = review.getUser().getId();

        if (!id.equals(currentUser)) {
            throw new NotFoundException(ErrorCode.NO_AUTHORIZATION_EXCEPTION
                    , ErrorCode.NO_AUTHORIZATION_EXCEPTION.getMessage());
        }

        reviewRepository.delete(review);
    }
}
