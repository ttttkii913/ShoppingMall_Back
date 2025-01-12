package org.shoppingmall.category;

import org.shoppingmall.category.domain.Category;
import org.shoppingmall.category.domain.CategoryType;
import org.shoppingmall.category.domain.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// CommandLineRunner - 프로젝트 시작 시 실행될 초기화 작업(카테고리 이넘들 값 자동으로 db에 저장)
@Component
public class CategoryInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public CategoryInitializer(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception { // 프로젝트 실행 시 실행될 메소드
        // categoryType의 값들을 저장
        for (CategoryType categoryType : CategoryType.values()) {
            if (!categoryRepository.findByCategoryType(categoryType).isPresent()) {
                // 새로운 Category 엔티티를 생성하고 저장
                categoryRepository.save(new Category(categoryType));
            }
        }
    }
}
