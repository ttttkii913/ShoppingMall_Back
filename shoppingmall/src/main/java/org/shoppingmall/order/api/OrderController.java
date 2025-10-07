package org.shoppingmall.order.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.shoppingmall.common.config.ApiResponseTemplate;
import org.shoppingmall.common.error.SuccessCode;
import org.shoppingmall.order.api.dto.request.OrderReqDto;
import org.shoppingmall.order.api.dto.response.OrderResDto;
import org.shoppingmall.order.application.OrderService;
import org.shoppingmall.user.application.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Tag(name = "주문 API", description = "주문 생성 API")
public class OrderController {

    private static final String ENCODED_ORDER_SUCCESS = URLEncoder.encode("주문 성공", StandardCharsets.UTF_8);

    private final UserService userService;
    private final OrderService orderService;

    @Operation(summary = "주문 생성", description = "인증된 사용자가 주문을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류. 관리자 문의.")
    })
    @PostMapping("/order")
    public ApiResponseTemplate<OrderResDto> createOrder(Principal principal) {
        OrderResDto orderResDto = orderService.createOrder(principal);
        return ApiResponseTemplate.successResponse(SuccessCode.ORDER_SAVE_SUCCESS, orderResDto);
    }
}
