package org.shoppingmall.cart.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "사용자가 장바구니에 상품 등록", description = "인증된 사용자가 장바구니에 상품을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류. 관리자 문의.")
    })
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CartResDto addToCart(@RequestBody CartReqDto cartReqDto, Principal principal) {
        Cart cart = cartService.addToCart(principal, cartReqDto);
        return new CartResDto(cart);
    }
}
