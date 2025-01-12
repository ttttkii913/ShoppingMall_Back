package org.shoppingmall.category.domain.repository;

import org.shoppingmall.category.domain.Category;
import org.shoppingmall.category.domain.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryType(CategoryType categoryType);
}
