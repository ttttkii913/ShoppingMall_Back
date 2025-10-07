package org.shoppingmall.cart.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.shoppingmall.cart.api.dto.request.CartReqDto;
import org.shoppingmall.cart.api.dto.response.CartResDto;
import org.shoppingmall.cart.application.CartService;
import org.shoppingmall.cart.domain.Cart;
import org.shoppingmall.common.config.CommonApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
@Tag(name = "장바구니 API", description = "Cart 관련 API")
@CommonApiResponse
public class CartController {
    private final CartService cartService;

    @Operation(summary = "사용자가 장바구니에 상품 등록", description = "인증된 사용자가 장바구니에 상품을 등록합니다.")
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CartResDto addToCart(@RequestBody CartReqDto cartReqDto, Principal principal) {
        Cart cart = cartService.addToCart(principal, cartReqDto);
        return new CartResDto(cart);
    }
}
