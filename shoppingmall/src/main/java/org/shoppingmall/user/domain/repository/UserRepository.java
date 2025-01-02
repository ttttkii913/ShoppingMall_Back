package org.shoppingmall.user.domain.repository;

import org.shoppingmall.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
