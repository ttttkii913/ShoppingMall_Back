package org.shoppingmall.admin.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.shoppingmall.admin.application.AdminService;
import org.shoppingmall.common.config.ApiResponseTemplate;
import org.shoppingmall.common.config.CommonApiResponse;
import org.shoppingmall.common.error.SuccessCode;
import org.shoppingmall.seller.api.dto.response.SellerInfoResDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
@CommonApiResponse
@RequiredArgsConstructor
@Tag(name = "관리자 API", description = "관리자 관련 API")
public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "관리자의 판매자 정보 승인", description = "관리자가 판매자의 회사 정보 등록을 승인합니다.")
    @PatchMapping("/approve")
    public ApiResponseTemplate<SellerInfoResDto> approveSeller(@RequestParam Long sellerId) {
        SellerInfoResDto sellerInfoResDto = adminService.approveSeller(sellerId);
        return ApiResponseTemplate.successResponse(SuccessCode.SELLER_APPROVE_SUCCESS, sellerInfoResDto);
    }

    @Operation(summary = "관리자의 판매자 정보 거절", description = "관리자가 판매자의 회사 정보 등록을 거절합니다.")
    @PatchMapping("/reject")
    public ApiResponseTemplate<SellerInfoResDto> rejectSeller(@RequestParam Long sellerId) {
        SellerInfoResDto sellerInfoResDto = adminService.rejectSeller(sellerId);
        return ApiResponseTemplate.successResponse(SuccessCode.SELLER_REJECT_SUCCESS, sellerInfoResDto);
    }

    @Operation(summary = "관리자가 대기 상태인 판매자 정보 전체 조회", description = "관리자가 대기 상태인 판매자 정보를 전체 조회합니다.")
    @GetMapping("/pending/info")
    public ApiResponseTemplate<List<SellerInfoResDto>> getPendingSellers() {
        List<SellerInfoResDto> sellerInfoResDtos = adminService.getPendingSellers();
        return ApiResponseTemplate.successResponse(SuccessCode.GET_SUCCESS, sellerInfoResDtos);
    }

    @Operation(summary = "관리자의 판매자 상세 정보 조회", description = "관리자가 판매자의 회사 상세 정보를 조회합니다.")
    @GetMapping("/seller/detail/info")
    public ApiResponseTemplate<SellerInfoResDto> getSellerInfo(@RequestParam Long sellerId) {
        SellerInfoResDto sellerInfoResDto = adminService.getSellerInfo(sellerId);
        return ApiResponseTemplate.successResponse(SuccessCode.GET_SUCCESS, sellerInfoResDto);
    }
}
