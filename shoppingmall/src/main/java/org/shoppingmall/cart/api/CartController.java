package org.shoppingmall.cart.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.shoppingmall.cart.api.dto.request.CartSaveReqDto;
import org.shoppingmall.cart.api.dto.request.CartUpdateReqDto;
import org.shoppingmall.cart.api.dto.response.CartListResDto;
import org.shoppingmall.cart.application.CartService;
import org.shoppingmall.cart.domain.Cart;
import org.shoppingmall.common.config.ApiResponseTemplate;
import org.shoppingmall.common.config.CommonApiResponse;
import org.shoppingmall.common.error.SuccessCode;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
@Tag(name = "장바구니 API", description = "Cart 관련 API")
@CommonApiResponse
public class CartController {

    private final CartService cartService;

    @Operation(summary = "사용자가 장바구니에 상품 옵션 등록", description = "인증된 사용자가 장바구니에 상품을 등록합니다.")
    @PostMapping("/save")
    public CartListResDto addToCart(@RequestBody CartSaveReqDto cartReqDto, Principal principal) {
        Cart cart = cartService.addToCart(principal, cartReqDto);
        return new CartListResDto(cart);
    }

    @Operation(summary = "사용자가 장바구니에서 상품 옵션 수정", description = "인증된 사용자가 장바구니에서 상품 옵션을 수정합니다.")
    @PatchMapping("/update")
    public ApiResponseTemplate<String> updateProductOption(@RequestBody CartUpdateReqDto cartUpdateReqDto, Principal principal) {
        cartService.updateProductOption(cartUpdateReqDto, principal);
        return ApiResponseTemplate.successWithNoContent(SuccessCode.CART_UPDATE_SUCCESS);
    }

    @Operation(summary = "사용자가 장바구니에서 상품 옵션 삭제", description = "인증된 사용자가 장바구니에서 상품 옵션을 삭제합니다.")
    @DeleteMapping("/delete")
    public ApiResponseTemplate<String> deleteProductOption(@RequestParam Long productOptionId, Principal principal) {
        cartService.deleteProductOption(productOptionId, principal);
        return ApiResponseTemplate.successWithNoContent(SuccessCode.CART_DELETE_SUCCESS);
    }

    @Operation(summary = "사용자가 장바구니에서 전체 리스트 조회", description = "인증된 사용자가 장바구니에서 전체 상품 리스트를 조회합니다.")
    @GetMapping("/list/view")
    public ApiResponseTemplate<CartListResDto> getCartList(Principal principal) {
        CartListResDto cartListResDto = cartService.getCartList(principal);
        return ApiResponseTemplate.successResponse(SuccessCode.GET_SUCCESS, cartListResDto);
    }
}
