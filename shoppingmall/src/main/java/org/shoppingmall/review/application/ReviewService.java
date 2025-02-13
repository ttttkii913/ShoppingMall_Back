package org.shoppingmall.review.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shoppingmall.common.config.S3Service;
import org.shoppingmall.order.domain.repository.OrderRepository;
import org.shoppingmall.product.domain.Product;
import org.shoppingmall.product.domain.repository.ProductRepository;
import org.shoppingmall.review.api.dto.request.ReviewReqDto;
import org.shoppingmall.review.api.dto.response.ReviewResDto;
import org.shoppingmall.review.domain.Review;
import org.shoppingmall.review.domain.repository.ReviewRepository;
import org.shoppingmall.user.domain.User;
import org.shoppingmall.user.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final S3Service s3Service;

    // 리뷰 등록
    public ReviewResDto reviewSave(Long productId, ReviewReqDto reviewReqDto
                                    , MultipartFile multipartFile, Principal principal) throws IOException {
        // 사용자 찾기
        Long id = Long.parseLong(principal.getName());
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다. id = " + id));

        // 상품 찾기
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("상품을 찾을 수 없습니다. id = " + productId));

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

    // 리뷰 조회



    // 리뷰 수정


    // 리뷰 삭제
}
