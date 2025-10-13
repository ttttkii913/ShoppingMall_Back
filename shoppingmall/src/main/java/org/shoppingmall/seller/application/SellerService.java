package org.shoppingmall.seller.application;

import lombok.RequiredArgsConstructor;
import org.shoppingmall.common.EntityFinderException;
import org.shoppingmall.common.error.ErrorCode;
import org.shoppingmall.common.exception.CustomException;
import org.shoppingmall.seller.api.dto.request.SellerInfoReqDto;
import org.shoppingmall.seller.api.dto.response.SellerInfoResDto;
import org.shoppingmall.seller.domain.Seller;
import org.shoppingmall.seller.domain.SellerStatus;
import org.shoppingmall.user.domain.User;
import org.shoppingmall.user.domain.UserStatus;
import org.shoppingmall.user.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor
@Transactional
public class SellerService {

    private final EntityFinderException entityFinder;
    private final UserRepository userRepository;

    // 판매자 등록
    public SellerInfoResDto registerSeller(SellerInfoReqDto sellerInfoReqDto, Principal principal) {
        User user = entityFinder.getUserFromPrincipal(principal);

        if (user.getUserStatus() == UserStatus.SELLER) {
            throw new CustomException(ErrorCode.ALREADY_SELLER, ErrorCode.ALREADY_SELLER.getMessage());
        }

        // 판매자 정보 생성
        Seller seller = Seller.builder()
                .businessName(sellerInfoReqDto.businessName())
                .businessNumber(sellerInfoReqDto.businessNumber())
                .contactNumber(sellerInfoReqDto.contactNumber())
                .bankName(sellerInfoReqDto.bankName())
                .accountNumber(sellerInfoReqDto.accountNumber())
                .sellerStatus(SellerStatus.PENDING)
                .user(user)
                .build();

        userRepository.save(user);

        return SellerInfoResDto.from(seller);
    }
}
