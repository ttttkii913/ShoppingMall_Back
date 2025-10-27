package org.shoppingmall.product.application;

import lombok.RequiredArgsConstructor;
import org.shoppingmall.category.domain.Category;
import org.shoppingmall.category.domain.CategoryType;
import org.shoppingmall.category.domain.repository.CategoryRepository;
import org.shoppingmall.common.EntityFinderException;
import org.shoppingmall.common.config.S3Service;
import org.shoppingmall.common.error.ErrorCode;
import org.shoppingmall.common.exception.CustomException;
import org.shoppingmall.common.exception.NotFoundException;
import org.shoppingmall.productoption.api.dto.request.ProductOptionReqDto;
import org.shoppingmall.productoption.api.dto.request.ProductOptionUpdateReqDto;
import org.shoppingmall.product.api.dto.request.ProductSaveReqDto;
import org.shoppingmall.product.api.dto.request.ProductUpdateReqDto;
import org.shoppingmall.product.api.dto.response.ProductInfoResDto;
import org.shoppingmall.product.api.dto.response.ProductListResDto;
import org.shoppingmall.product.domain.Product;
import org.shoppingmall.product.domain.repository.ProductRepository;
import org.shoppingmall.productoption.domain.ProductOption;
import org.shoppingmall.productoption.domain.repository.ProductOptionRepository;
import org.shoppingmall.user.domain.User;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final CategoryRepository categoryRepository;
    private final S3Service s3Service;
    private final EntityFinderException entityFinder;

    // 등록
    @Transactional
    public ProductInfoResDto productSave(ProductSaveReqDto productSaveReqDto
                                    , MultipartFile multipartFile, Principal principal) throws IOException {
        User user = entityFinder.getUserFromPrincipal(principal);
        // category 찾기
        CategoryType categoryType = productSaveReqDto.categoryType();
        Category category = categoryRepository.findByCategoryType(categoryType)
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND_EXCEPTION,
                        ErrorCode.CATEGORY_NOT_FOUND_EXCEPTION.getMessage()));

        // 이미지 업로드를 선택적으로 넘기면서 이미지가 있을 경우에만 업로드
        String productImage = null; // 이미지 URL을 저장할 변수
        if (multipartFile != null && !multipartFile.isEmpty()) {
            productImage = s3Service.upload(multipartFile, "product");
        }

        // 저장
        Product product = productSaveReqDto.toEntity(productImage, user, category);
        productRepository.save(product);

        // 상품 옵션 저장
        if (productSaveReqDto.options() != null) {
            for (ProductOptionReqDto productOptionReqDto : productSaveReqDto.options()) {
                ProductOption option = ProductOption.builder()
                        .product(product)
                        .size(productOptionReqDto.size())
                        .productOptionColor(productOptionReqDto.productOptionColor())
                        .stock(productOptionReqDto.stock())
                        .build();
                productOptionRepository.save(option);
            }
        }
        return ProductInfoResDto.from(product);

    }

    // 전체 상품 조회
    public ProductListResDto getProductList() {
        List<Product> products = productRepository.findAll();
        List<ProductInfoResDto> productInfoResDtoList = products.stream()
                .map(ProductInfoResDto::from)
                .toList();
        return ProductListResDto.from(productInfoResDtoList);
    }

    // 상세 상품 조회
    public ProductInfoResDto getProductDetail(Long productId) {
        Product product = entityFinder.getProductById(productId);
        return ProductInfoResDto.from(product);
    }

    // 카테고리별 상품 리스트 조회
    public ProductListResDto getProductCategory(Long categoryId, String sort) {
        Sort sortOrder;

        if ("popular".equals(sort)) {
            sortOrder = Sort.by(Sort.Direction.DESC, "likeCount");
        } else {
            sortOrder = Sort.by(Sort.Direction.DESC, "productCreatedAt");
        }

        List<Product> products = productRepository.findByCategoryId(categoryId, sortOrder);
        List<ProductInfoResDto> productInfoResDtoList = products.stream()
                .map(ProductInfoResDto::from)
                .collect(Collectors.toList());

        return new ProductListResDto(productInfoResDtoList);
    }

    // 상품 수정
    @Transactional
    public ProductInfoResDto productUpdate(Long productId, ProductUpdateReqDto productUpdateReqDto, MultipartFile productImage, Principal principal) throws IOException {

        Product product = entityFinder.getProductById(productId);

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

    // 상품 옵션 수정
    @Transactional
    public ProductOption updateProductOption(ProductOptionUpdateReqDto reqDto) {
        ProductOption productOption = entityFinder.getProductOptionById(reqDto.productOptionId());

        productOption.update(reqDto.size(), reqDto.stock(), reqDto.productOptionColor());

        return productOptionRepository.save(productOption);
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
