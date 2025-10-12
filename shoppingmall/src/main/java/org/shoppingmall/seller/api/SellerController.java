package org.shoppingmall.seller.api;

import lombok.RequiredArgsConstructor;
import org.shoppingmall.common.config.ApiResponseTemplate;
import org.shoppingmall.common.config.CommonApiResponse;
import org.shoppingmall.common.error.SuccessCode;
import org.shoppingmall.seller.api.dto.request.SellerInfoReqDto;
import org.shoppingmall.seller.api.dto.response.SellerInfoResDto;
import org.shoppingmall.seller.application.SellerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seller")
@CommonApiResponse
public class SellerController {

    private final SellerService sellerService;

    @PostMapping("/register")
    public ApiResponseTemplate<SellerInfoResDto> registerSeller(@RequestBody SellerInfoReqDto sellerInfoReqDto,
                                                                Principal principal) {
        SellerInfoResDto sellerInfoResDto = sellerService.registerSeller(sellerInfoReqDto, principal);
        return ApiResponseTemplate.successResponse(SuccessCode.SELLER_REGISTER_SUCCESS, sellerInfoResDto);
    }
}
