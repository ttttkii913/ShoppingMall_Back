package org.shoppingmall.user.domain.repository;

import org.shoppingmall.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String userEmail);
    boolean existsByEmail(String email);

}
