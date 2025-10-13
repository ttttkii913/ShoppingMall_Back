package org.shoppingmall.seller.domain.repository;

import org.shoppingmall.seller.domain.Seller;
import org.shoppingmall.seller.domain.SellerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    List<Seller> findAllBySellerStatus(SellerStatus status);
}
