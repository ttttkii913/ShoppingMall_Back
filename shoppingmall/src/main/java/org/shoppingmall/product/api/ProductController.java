package org.shoppingmall.product.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.shoppingmall.category.domain.Category;
import org.shoppingmall.common.config.ApiResponseTemplate;
import org.shoppingmall.common.config.CommonApiResponse;
import org.shoppingmall.common.error.SuccessCode;
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
@Tag(name = "상품 API", description = "상품 CRUD API")
@CommonApiResponse
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "판매자가 상품 등록", description = "판매자가 상품을 등록합니다.")
    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponseTemplate<ProductInfoResDto> productSave(@RequestPart("product") ProductSaveReqDto productSaveReqDto,
                                                              @RequestParam(value = "productImage", required = false) MultipartFile productImage,
                                                              Principal principal) throws IOException {
        ProductInfoResDto productInfoResDto = productService.productSave(productSaveReqDto, productImage, principal);
        return ApiResponseTemplate.successResponse(SuccessCode.PRODUCT_SAVE_SUCCESS, productInfoResDto);
    }

    @Operation(summary = "메인 페이지 - 전체 상품 조회", description = "모든 사용자가 메인페이지에서 상품을 볼 수 있습니다.")
    @GetMapping("/all")
    public ApiResponseTemplate<ProductListResDto> productGet() {
        ProductListResDto productListResDto = productService.productGet();
        return ApiResponseTemplate.successResponse(SuccessCode.GET_SUCCESS, productListResDto);

    }

    @Operation(summary = "판매자가 상품 수정", description = "판매자가 상품을 수정합니다.")
    @PatchMapping(value = "/update/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponseTemplate<ProductInfoResDto> productUpdate(@PathVariable("productId") Long productId,
                                                                @RequestPart ProductUpdateReqDto productUpdateReqDto,
                                                                @RequestParam(value = "productImage", required = false) MultipartFile productImage,
                                                                Principal principal) throws IOException {
        ProductInfoResDto productInfoResDto = productService.productUpdate(productId, productUpdateReqDto, productImage, principal);
        return ApiResponseTemplate.successResponse(SuccessCode.PRODUCT_UPDATE_SUCCESS, productInfoResDto);
    }

    @Operation(summary = "판매자가 상품 삭제", description = "판매자가 상품을 삭제합니다.")
    @DeleteMapping("/delete/{productId}")
    public ApiResponseTemplate<String> productDelete(@PathVariable("productId") Long productId, Principal principal) {
        productService.productDelete(productId, principal);
        return ApiResponseTemplate.successWithNoContent(SuccessCode.PRODUCT_DELETE_SUCCESS);
    }
}
