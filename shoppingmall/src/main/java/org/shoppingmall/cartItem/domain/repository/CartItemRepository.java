package org.shoppingmall.cartItem.domain.repository;

import org.shoppingmall.cart.domain.Cart;
import org.shoppingmall.cartItem.domain.CartItem;
import org.shoppingmall.productoption.domain.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByProductOption(ProductOption productOption);

}
