package org.shoppingmall.order.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.shoppingmall.common.config.ApiResponseTemplate;
import org.shoppingmall.common.config.CommonApiResponse;
import org.shoppingmall.common.error.SuccessCode;
import org.shoppingmall.order.api.dto.response.OrderResDto;
import org.shoppingmall.order.application.OrderService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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

    @Operation(summary = "주문 전체 조회", description = "인증된 사용자의 모든 주문 목록을 조회합니다.")
    @GetMapping("/list")
    public ApiResponseTemplate<List<OrderResDto>> getAllOrders(Principal principal) {
        List<OrderResDto> orders = orderService.getAllOrders(principal);
        return ApiResponseTemplate.successResponse(SuccessCode.GET_SUCCESS, orders);
    }

    @Operation(summary = "주문 상세 조회", description = "주문 id로 주문 상세 정보를 조회합니다.")
    @GetMapping("/detail")
    public ApiResponseTemplate<OrderResDto> getOrderDetail(@RequestParam Long orderId, Principal principal) {
        OrderResDto orderResDto = orderService.getOrderDetail(orderId, principal);
        return ApiResponseTemplate.successResponse(SuccessCode.GET_SUCCESS, orderResDto);
    }

}
