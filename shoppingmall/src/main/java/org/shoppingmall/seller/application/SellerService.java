package org.shoppingmall.seller.application;

import lombok.RequiredArgsConstructor;
import org.shoppingmall.common.EntityFinderException;
import org.shoppingmall.common.error.ErrorCode;
import org.shoppingmall.common.exception.CustomException;
import org.shoppingmall.seller.api.dto.request.SellerInfoReqDto;
import org.shoppingmall.seller.api.dto.response.SellerInfoResDto;
import org.shoppingmall.seller.domain.Seller;
import org.shoppingmall.seller.domain.SellerStatus;
import org.shoppingmall.seller.domain.repository.SellerRepository;
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
    private final SellerRepository sellerRepository;

    // 판매자 등록
    public SellerInfoResDto registerSeller(SellerInfoReqDto sellerInfoReqDto, Principal principal) {
        User user = entityFinder.getUserFromPrincipal(principal);

        // 판매자 정보 생성
        Seller seller = Seller.builder()
                .businessName(sellerInfoReqDto.businessName())
                .businessNumber(sellerInfoReqDto.businessNumber())
                .businessEmail(sellerInfoReqDto.businessEmail())
                .contactNumber(sellerInfoReqDto.contactNumber())
                .bankName(sellerInfoReqDto.bankName())
                .accountNumber(sellerInfoReqDto.accountNumber())
                .sellerStatus(SellerStatus.PENDING)
                .user(user)
                .build();

        Seller savedSeller = sellerRepository.save(seller);

        user.changeRole(UserStatus.ROLE_PENDING);
        userRepository.save(user);

        return SellerInfoResDto.from(savedSeller);
    }
}
