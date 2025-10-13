package org.shoppingmall.seller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "판매자 정보 등록 API", description = "판매자 정보 등록 API")
public class SellerController {

    private final SellerService sellerService;

    @Operation(summary = "판매자 정보 등록", description = "판매자가 자신의 회사 정보를 등록합니다. (등록 후 관리자의 승인 필요)")
    @PostMapping("/register")
    public ApiResponseTemplate<SellerInfoResDto> registerSeller(@RequestBody SellerInfoReqDto sellerInfoReqDto,
                                                                Principal principal) {
        SellerInfoResDto sellerInfoResDto = sellerService.registerSeller(sellerInfoReqDto, principal);
        return ApiResponseTemplate.successResponse(SuccessCode.SELLER_REGISTER_SUCCESS, sellerInfoResDto);
    }
}
