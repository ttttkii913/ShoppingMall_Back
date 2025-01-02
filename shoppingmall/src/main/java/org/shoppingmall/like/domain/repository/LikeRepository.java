package org.shoppingmall.like.domain.repository;

import org.shoppingmall.like.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
