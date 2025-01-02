package org.shoppingmall.product.domain.repository;

import org.shoppingmall.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
