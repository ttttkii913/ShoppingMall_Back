package org.shoppingmall.admin.application;

import lombok.RequiredArgsConstructor;
import org.shoppingmall.common.EntityFinderException;
import org.shoppingmall.seller.api.dto.response.SellerInfoResDto;
import org.shoppingmall.seller.domain.Seller;
import org.shoppingmall.seller.domain.SellerStatus;
import org.shoppingmall.seller.domain.repository.SellerRepository;
import org.shoppingmall.user.domain.User;
import org.shoppingmall.user.domain.UserStatus;
import org.shoppingmall.user.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final UserRepository userRepository;
    private final EntityFinderException entityFinder;
    private final SellerRepository sellerRepository;

    // 관리자 승인
    public SellerInfoResDto approveSeller(Long sellerId) {
        Seller seller = entityFinder.getSellerById(sellerId);

        seller.sellerApprove(); // approve로 변경

        // 판매자로 변경
        User user = seller.getUser();
        user.changeRole(UserStatus.SELLER);

        userRepository.save(user);

        return SellerInfoResDto.from(seller);
    }

    // 관리자 거절
    public SellerInfoResDto rejectSeller(Long sellerId) {
        Seller seller = entityFinder.getSellerById(sellerId);

        seller.sellerReject();

        return SellerInfoResDto.from(seller);
    }

    // 승인 대기 상태 판매자 정보 전체 조회
    public List<SellerInfoResDto> getPendingSellers() {
        return sellerRepository.findAllBySellerStatus(SellerStatus.PENDING)
                .stream()
                .map(SellerInfoResDto::from)
                .toList();
    }

    // 판매자 정보 상세 조회
    public SellerInfoResDto getSellerInfo(Long sellerId) {
        Seller seller = entityFinder.getSellerById(sellerId);
        return SellerInfoResDto.from(seller);
    }
}
