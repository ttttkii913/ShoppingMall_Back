package org.shoppingmall.cart.api;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.shoppingmall.cart.api.dto.request.CartReqDto;
import org.shoppingmall.cart.api.dto.response.CartResDto;
import org.shoppingmall.cart.application.CartService;
import org.shoppingmall.cart.domain.Cart;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    // 장바구니에 상품 추가
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CartResDto addToCart(@RequestBody CartReqDto cartReqDto, Principal principal) {
        Cart cart = cartService.addToCart(principal, cartReqDto);
        return new CartResDto(cart);
    }
}
