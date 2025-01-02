package org.shoppingmall.cart.domain.repository;

import org.shoppingmall.cart.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
