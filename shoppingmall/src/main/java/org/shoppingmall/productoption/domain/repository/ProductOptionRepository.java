package org.shoppingmall.productoption.domain.repository;

import org.shoppingmall.productoption.domain.ProductOption;
import org.shoppingmall.productoption.domain.ProductOptionColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
    Optional<ProductOption> findByProductIdAndSizeAndProductOptionColor(
            Long productId, String size, ProductOptionColor color);

}
