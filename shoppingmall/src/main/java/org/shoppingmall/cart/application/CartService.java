package org.shoppingmall.cart.application;

import lombok.RequiredArgsConstructor;
import org.shoppingmall.cart.api.dto.request.CartSaveReqDto;
import org.shoppingmall.cart.api.dto.request.CartUpdateReqDto;
import org.shoppingmall.cart.api.dto.response.CartListResDto;
import org.shoppingmall.cart.domain.Cart;
import org.shoppingmall.cart.domain.repository.CartRepository;
import org.shoppingmall.cartItem.domain.CartItem;
import org.shoppingmall.cartItem.domain.repository.CartItemRepository;
import org.shoppingmall.common.EntityFinderException;
import org.shoppingmall.common.error.ErrorCode;
import org.shoppingmall.common.exception.CustomException;
import org.shoppingmall.productoption.domain.ProductOption;
import org.shoppingmall.productoption.domain.repository.ProductOptionRepository;
import org.shoppingmall.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final EntityFinderException entityFinder;
    private final CartItemRepository cartItemRepository;
    private final ProductOptionRepository productOptionRepository;

    // 장바구니에 추가
    @Transactional
    public Cart addToCart(Principal principal, CartSaveReqDto cartReqDto) {
        //  user 찾기
        User user = entityFinder.getUserFromPrincipal(principal);

        // productOption 찾기
        ProductOption productOption = entityFinder.getProductOptionById(cartReqDto.productOptionId());

        // 장바구니 조회, 없으면 새로 생성
        Cart cart = cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = Cart.builder().user(user).build();
            return cartRepository.save(newCart);
        });

        // 장바구니에 상품 추가
        CartItem cartItem = CartItem.builder()
                .quantity(cartReqDto.quantity())
                .productOption(productOption)
                .build();

        cart.addCartItem(cartItem);
        return cartRepository.save(cart);
    }

    // 장바구니에서 수정
    @Transactional
    public void updateProductOption(CartUpdateReqDto cartUpdateReqDto, Principal principal) {
        User user = entityFinder.getUserFromPrincipal(principal);
        Cart cart = getCartByUser(user);
        CartItem cartItem = getCartItemByProductOptionId(cartUpdateReqDto.productOptionId());

        // 새 ProductOption 조회 (같은 상품 내 옵션, size/색상 기준)
        ProductOption newOption = productOptionRepository.findByProductIdAndSizeAndProductOptionColor(
                cartItem.getProductOption().getProduct().getId(),
                cartUpdateReqDto.size(),
                cartItem.getProductOption().getProductOptionColor() // 기존 색상 유지
                ).orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_OPTION_NOT_FOUND_EXCEPTION,
                    ErrorCode.PRODUCT_OPTION_NOT_FOUND_EXCEPTION.getMessage()));

        cart.updateCartItem(cartItem, newOption, cartUpdateReqDto.quantity());
        cartRepository.save(cart);
    }

    // 장바구니에서 삭제
    @Transactional
    public void deleteProductOption(Long productOptionId, Principal principal) {
        User user = entityFinder.getUserFromPrincipal(principal);
        Cart cart = getCartByUser(user);
        CartItem cartItem = getCartItemByProductOptionId(productOptionId);

        // Cart에서 제거
        cart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);
    }

    // 장바구니 리스트 조회
    @Transactional(readOnly = true)
    public CartListResDto getCartList(Principal principal) {
        User user = entityFinder.getUserFromPrincipal(principal);
        Cart cart = getCartByUser(user);

        return new CartListResDto(cart);
    }

    // 공통 메소드
    private Cart getCartByUser(User user) {
        return cartRepository.findByUser(user)
                .orElseThrow(() -> new CustomException(ErrorCode.CART_NOT_FOUND_EXCEPTION,
                        ErrorCode.CART_NOT_FOUND_EXCEPTION.getMessage()));
    }

    private CartItem getCartItemByProductOptionId(Long productOptionId) {
        ProductOption productOption = entityFinder.getProductOptionById(productOptionId);

        return cartItemRepository.findByProductOption(productOption)
                .orElseThrow(() -> new CustomException(ErrorCode.CARTITEM_NOT_FOUND_EXCEPTION,
                        ErrorCode.CARTITEM_NOT_FOUND_EXCEPTION.getMessage()));
    }
}
