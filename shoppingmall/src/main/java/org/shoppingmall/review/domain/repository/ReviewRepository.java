package org.shoppingmall.review.domain.repository;

import org.shoppingmall.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
