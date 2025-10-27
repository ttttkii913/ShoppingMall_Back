package org.shoppingmall.product.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.shoppingmall.common.config.ApiResponseTemplate;
import org.shoppingmall.common.config.CommonApiResponse;
import org.shoppingmall.common.error.SuccessCode;
import org.shoppingmall.productoption.api.dto.request.ProductOptionUpdateReqDto;
import org.shoppingmall.product.api.dto.request.ProductSaveReqDto;
import org.shoppingmall.product.api.dto.request.ProductUpdateReqDto;
import org.shoppingmall.product.api.dto.response.ProductInfoResDto;
import org.shoppingmall.product.api.dto.response.ProductListResDto;
import org.shoppingmall.product.application.ProductService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
@Tag(name = "상품 조회 API", description = "상품 조회 API")
@CommonApiResponse
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "메인 페이지 - 전체 상품 조회", description = "모든 사용자가 메인페이지에서 상품을 볼 수 있습니다.")
    @GetMapping("/all")
    public ApiResponseTemplate<ProductListResDto> getProductList() {
        ProductListResDto productListResDto = productService.getProductList();
        return ApiResponseTemplate.successResponse(SuccessCode.GET_SUCCESS, productListResDto);
    }

    @Operation(summary = "메인 페이지 - 상세 상품 조회", description = "모든 사용자가 상품 상세를 볼 수 있습니다.")
    @GetMapping("/detail")
    public ApiResponseTemplate<ProductInfoResDto> getProductDetail(@RequestParam Long productId) {
        ProductInfoResDto productInfoResDto = productService.getProductDetail(productId);
        return ApiResponseTemplate.successResponse(SuccessCode.GET_SUCCESS, productInfoResDto);
    }

    @Operation(summary = "메인 페이지 - 카테고리별 상품 리스트 조회", description = "모든 사용자가 카테고리별 상품 리스트를 최신순, 인기순으로 조회합니다.")
    @GetMapping("/category")
    public ApiResponseTemplate<ProductListResDto> getProductCategory(@RequestParam Long categoryId, @RequestParam(defaultValue = "latest") String sort) {
        ProductListResDto productListResDto = productService.getProductCategory(categoryId, sort);
        return ApiResponseTemplate.successResponse(SuccessCode.GET_SUCCESS, productListResDto);
    }
}
