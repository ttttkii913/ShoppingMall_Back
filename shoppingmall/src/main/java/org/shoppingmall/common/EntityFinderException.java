package org.shoppingmall.common;

import lombok.RequiredArgsConstructor;
import org.shoppingmall.cart.domain.Cart;
import org.shoppingmall.cart.domain.repository.CartRepository;
import org.shoppingmall.comment.domain.Comment;
import org.shoppingmall.comment.domain.repository.CommentRepository;
import org.shoppingmall.common.error.ErrorCode;
import org.shoppingmall.common.exception.CustomException;
import org.shoppingmall.like.domain.Like;
import org.shoppingmall.like.domain.repository.LikeRepository;
import org.shoppingmall.order.domain.Order;
import org.shoppingmall.order.domain.repository.OrderRepository;
import org.shoppingmall.product.domain.Product;
import org.shoppingmall.product.domain.repository.ProductRepository;
import org.shoppingmall.review.domain.Review;
import org.shoppingmall.review.domain.repository.ReviewRepository;
import org.shoppingmall.user.domain.User;
import org.shoppingmall.user.domain.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
@RequiredArgsConstructor
public class EntityFinderException {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final LikeRepository likeRepository;
    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final CommentRepository commentRepository;

    public User getUserFromPrincipal(Principal principal) {
        Long id = Long.parseLong(principal.getName());
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND_EXCEPTION,
                        ErrorCode.USER_NOT_FOUND_EXCEPTION.getMessage() + id));
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND_EXCEPTION
                        , ErrorCode.USER_NOT_FOUND_EXCEPTION.getMessage() + userId));
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND_EXCEPTION
                , ErrorCode.PRODUCT_NOT_FOUND_EXCEPTION.getMessage() + productId));
    }

    public Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId).orElseThrow(
                () -> new CustomException(ErrorCode.CART_NOT_FOUND_EXCEPTION
                        , ErrorCode.CART_NOT_FOUND_EXCEPTION.getMessage() + cartId));
    }

    public Like getLikeById(Long likeId) {
        return likeRepository.findById(likeId).orElseThrow(
                () -> new CustomException(ErrorCode.LIKE_NOT_FOUND_EXCEPTION
                        , ErrorCode.LIKE_NOT_FOUND_EXCEPTION.getMessage() + likeId));
    }

    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(
                () -> new CustomException(ErrorCode.REVIEW_NOT_FOUND_EXCEPTION
                        , ErrorCode.REVIEW_NOT_FOUND_EXCEPTION.getMessage() + reviewId));
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                () -> new CustomException(ErrorCode.ORDER_NOT_FOUND_EXCEPTION
                        , ErrorCode.ORDER_NOT_FOUND_EXCEPTION.getMessage() + orderId));
    }

    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(ErrorCode.COMMENT_NOT_FOUND_EXCEPTION
                        , ErrorCode.COMMENT_NOT_FOUND_EXCEPTION.getMessage() + commentId));
    }
}
