package org.shoppingmall.cart.application;

import lombok.RequiredArgsConstructor;
import org.shoppingmall.cart.api.dto.request.CartReqDto;
import org.shoppingmall.cart.domain.Cart;
import org.shoppingmall.cart.domain.repository.CartRepository;
import org.shoppingmall.cartItem.domain.CartItem;
import org.shoppingmall.common.EntityFinderException;
import org.shoppingmall.product.domain.Product;
import org.shoppingmall.product.domain.repository.ProductRepository;
import org.shoppingmall.user.domain.User;
import org.shoppingmall.user.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final EntityFinderException entityFinder;

    @Transactional
    public Cart addToCart(Principal principal, CartReqDto cartReqDto) {
        //  user 찾기
        User user = entityFinder.getUserFromPrincipal(principal);

        // product 찾기
        Product product = entityFinder.getProductById(cartReqDto.productId());

        // 장바구니 조회, 없으면 새로 생성
        Cart cart = cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = Cart.builder().user(user).build();
            return cartRepository.save(newCart);
        });

        // 장바구니에 상품 추가
        CartItem cartItem = CartItem.builder()
                .quantity(cartReqDto.quantity())
                .product(product)
                .build();

        cart.addCartItem(cartItem);
        cartRepository.save(cart);

        return cart;
    }
}
