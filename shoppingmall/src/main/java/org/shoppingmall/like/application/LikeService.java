package org.shoppingmall.like.application;

import lombok.RequiredArgsConstructor;
import org.shoppingmall.common.error.ErrorCode;
import org.shoppingmall.common.exception.NotFoundException;
import org.shoppingmall.like.domain.Like;
import org.shoppingmall.like.domain.LikeType;
import org.shoppingmall.like.domain.repository.LikeRepository;
import org.shoppingmall.product.domain.Product;
import org.shoppingmall.product.domain.repository.ProductRepository;
import org.shoppingmall.user.domain.User;
import org.shoppingmall.user.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // 좋아요 등록
    public void likeSave(Long productId, LikeType likeType, Principal principal) {
        Long id = Long.parseLong(principal.getName());
        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorCode.USER_NOT_FOUND_EXCEPTION
                        , ErrorCode.USER_NOT_FOUND_EXCEPTION.getMessage()));

        Product product = productRepository.findById(productId).orElseThrow(
                () -> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND_EXCEPTION
                        , ErrorCode.PRODUCT_NOT_FOUND_EXCEPTION.getMessage()));

        if (likeRepository.findByUserAndProduct(user, product).isPresent()) {
            throw new NotFoundException(ErrorCode.ALREADY_LIKE_PRODUCT
                    , ErrorCode.ALREADY_LIKE_PRODUCT.getMessage());
        }

        Like like = Like.of(user, product, likeType);
        product.increaseLikeCount(); // 상품의 좋아요 수 증가
        likeRepository.save(like);
    }

    // 좋아요 취소
    public void likeDelete(Long productId, Principal principal) {
        Long id = Long.parseLong(principal.getName());
        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorCode.USER_NOT_FOUND_EXCEPTION
                        , ErrorCode.USER_NOT_FOUND_EXCEPTION.getMessage()));

        Product product = productRepository.findById(productId).orElseThrow(
                () -> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND_EXCEPTION
                        , ErrorCode.PRODUCT_NOT_FOUND_EXCEPTION.getMessage()));

        // 사용자가 좋아요를 누르지 않았을 때 -> likeCount가 이미 0일 때 또 삭제를 시도하면 좋아요를 누르지 않았다고 에러 줌
        Optional<Like> existingLikes = likeRepository.findByUserAndProduct(user, product);
        if (existingLikes.isEmpty()) {
            throw new NotFoundException(ErrorCode.LIKE_NOT_FOUND_EXCEPTION
                    , ErrorCode.LIKE_NOT_FOUND_EXCEPTION.getMessage());
        }

        Like like = existingLikes.get();
        product.decreaseLikeCount(); // 상품의 좋아요 개수 감소
        likeRepository.delete(like);
    }
}
