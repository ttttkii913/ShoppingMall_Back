package org.shoppingmall.product.application;

import lombok.RequiredArgsConstructor;
import org.shoppingmall.category.domain.Category;
import org.shoppingmall.category.domain.CategoryType;
import org.shoppingmall.category.domain.repository.CategoryRepository;
import org.shoppingmall.common.config.S3Service;
import org.shoppingmall.common.error.ErrorCode;
import org.shoppingmall.common.exception.NotFoundException;
import org.shoppingmall.product.api.dto.request.ProductSaveReqDto;
import org.shoppingmall.product.api.dto.request.ProductUpdateReqDto;
import org.shoppingmall.product.api.dto.response.ProductInfoResDto;
import org.shoppingmall.product.api.dto.response.ProductListResDto;
import org.shoppingmall.product.domain.Product;
import org.shoppingmall.product.domain.repository.ProductRepository;
import org.shoppingmall.user.domain.User;
import org.shoppingmall.user.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final S3Service s3Service;

    // 등록
    @Transactional
    public ProductInfoResDto productSave(ProductSaveReqDto productSaveReqDto
                                    , MultipartFile multipartFile, Principal principal) throws IOException {
        //  user 찾기
        Long id = Long.parseLong(principal.getName());
        User user = userRepository.findById(id).orElseThrow( () -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // category 찾기
        CategoryType categoryType = productSaveReqDto.categoryType();
        Category category = categoryRepository.findByCategoryType(categoryType).orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));


        // 이미지 업로드를 선택적으로 넘기면서 이미지가 있을 경우에만 업로드
        String productImage = null; // 이미지 URL을 저장할 변수
        if (multipartFile != null && !multipartFile.isEmpty()) {
            productImage = s3Service.upload(multipartFile, "product");
        }

        // 저장
        Product product = productSaveReqDto.toEntity(productImage, user, category);
        productRepository.save(product);

        return ProductInfoResDto.from(product);

    }

    // 조회
    public ProductListResDto productGet() {
        List<Product> products = productRepository.findAll();
        List<ProductInfoResDto> productInfoResDtoList = products.stream()
                .map(ProductInfoResDto::from)
                .toList();
        return ProductListResDto.from(productInfoResDtoList);
    }

    // 수정
    @Transactional
    public ProductInfoResDto productUpdate(Long productId, ProductUpdateReqDto productUpdateReqDto, MultipartFile productImage, Principal principal) throws IOException {

        Product product = productRepository.findById(productId).orElseThrow( () -> new NotFoundException(ErrorCode.USER_NOT_FOUND_EXCEPTION, ErrorCode.USER_NOT_FOUND_EXCEPTION.getMessage()));

        // 수정 권한 확인
        Long id = Long.parseLong(principal.getName());
        Long currentUser = product.getUser().getId();

        if (!id.equals(currentUser)) {
            throw new NotFoundException(ErrorCode.NO_AUTHORIZATION_EXCEPTION
                    , ErrorCode.NO_AUTHORIZATION_EXCEPTION.getMessage());
        }

        product.update(productUpdateReqDto);

        // 이미지가 있을 경우 S3에 업로드하고 URL 업데이트
        if (productImage != null && !productImage.isEmpty()) {
            String imageUrl = s3Service.upload(productImage, "product");
            product.updateImage(imageUrl);
        }

        productRepository.save(product);
        return ProductInfoResDto.from(product);

    }

    // 삭제
    public void productDelete(Long productId, Principal principal) {
        //  user 찾기
        Long id = Long.parseLong(principal.getName());

        Product product = productRepository.findById(productId).orElseThrow( () -> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND_EXCEPTION, ErrorCode.PRODUCT_NOT_FOUND_EXCEPTION.getMessage()));

        // 수정 권한 확인
        Long currentUser = product.getUser().getId();

        if (!id.equals(currentUser)) {
            throw new NotFoundException(ErrorCode.NO_AUTHORIZATION_EXCEPTION
                    , ErrorCode.NO_AUTHORIZATION_EXCEPTION.getMessage());
        }

        productRepository.delete(product);
    }
}
