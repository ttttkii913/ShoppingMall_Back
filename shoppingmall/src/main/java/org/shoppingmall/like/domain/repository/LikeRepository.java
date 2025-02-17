package org.shoppingmall.like.domain.repository;

import org.shoppingmall.like.domain.Like;
import org.shoppingmall.product.domain.Product;
import org.shoppingmall.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndProduct(User user, Product product);
}
