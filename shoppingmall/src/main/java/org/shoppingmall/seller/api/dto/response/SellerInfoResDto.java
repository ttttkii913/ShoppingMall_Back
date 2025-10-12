package org.shoppingmall.seller.api.dto.response;

import org.shoppingmall.seller.domain.Seller;

public record SellerInfoResDto(
        Long id,
        String businessName,
        String businessNumber,
        String businessEmail,
        String contactNumber,
        String bankName,
        String accountNumber
) {
    public static SellerInfoResDto from(Seller seller) {
        return new SellerInfoResDto(
                seller.getId(),
                seller.getBusinessName(),
                seller.getBusinessNumber(),
                seller.getBusinessEmail(),
                seller.getContactNumber(),
                seller.getBankName(),
                seller.getAccountNumber()
        );
    }
}
