package org.shoppingmall.review.domain.repository;

import org.shoppingmall.review.api.dto.response.ReviewResDto;
import org.shoppingmall.review.domain.Review;
import org.shoppingmall.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<List<Review>> findByUserId(Long userId);
}
