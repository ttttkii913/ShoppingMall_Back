package org.shoppingmall.cart.domain.repository;

import org.shoppingmall.cart.domain.Cart;
import org.shoppingmall.cartItem.domain.CartItem;
import org.shoppingmall.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
