package org.shoppingmall.order.api;

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
public class OrderController {

    private static final String ENCODED_ORDER_SUCCESS = URLEncoder.encode("주문 성공", StandardCharsets.UTF_8);

    private final UserService userService;
    private final OrderService orderService;

    @PostMapping("/order")
    public ApiResponseTemplate<OrderResDto> createOrder(Principal principal) {
        OrderResDto orderResDto = orderService.createOrder(principal);
        return ApiResponseTemplate.successResponse(orderResDto, SuccessCode.ORDER_SAVE_SUCCESS);
    }
}
