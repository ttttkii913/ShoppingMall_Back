package org.shoppingmall.seller.api.dto.request;

public record SellerInfoReqDto(
        String businessName,
        String businessNumber,
        String businessEmail,
        String contactNumber,
        String bankName,
        String accountNumber
) {
}
