package org.shoppingmall.order.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.shoppingmall.common.config.ApiResponseTemplate;
import org.shoppingmall.common.config.CommonApiResponse;
import org.shoppingmall.common.error.SuccessCode;
import org.shoppingmall.order.api.dto.response.OrderResDto;
import org.shoppingmall.order.application.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Tag(name = "주문 API", description = "주문 생성 API")
@CommonApiResponse
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "주문 생성", description = "인증된 사용자가 주문을 생성합니다.")
    @PostMapping("/save")
    public ApiResponseTemplate<OrderResDto> createOrder(Principal principal) {
        OrderResDto orderResDto = orderService.createOrder(principal);
        return ApiResponseTemplate.successResponse(SuccessCode.ORDER_SAVE_SUCCESS, orderResDto);
    }
}
