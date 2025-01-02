package org.shoppingmall.category.domain.repository;

import org.shoppingmall.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
