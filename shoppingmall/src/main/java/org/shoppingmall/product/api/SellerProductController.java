package org.shoppingmall.product.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.shoppingmall.common.config.ApiResponseTemplate;
import org.shoppingmall.common.config.CommonApiResponse;
import org.shoppingmall.common.error.SuccessCode;
import org.shoppingmall.product.api.dto.request.ProductSaveReqDto;
import org.shoppingmall.product.api.dto.request.ProductUpdateReqDto;
import org.shoppingmall.product.api.dto.response.ProductInfoResDto;
import org.shoppingmall.product.api.dto.response.ProductListResDto;
import org.shoppingmall.product.application.ProductService;
import org.shoppingmall.productoption.api.dto.request.ProductOptionUpdateReqDto;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('SELLER')")
@RequestMapping("/seller/product")
@Tag(name = "판매자 상품 관리 API", description = "판매자 상품 CRUD API")
@CommonApiResponse
public class SellerProductController {

    private final ProductService productService;

    @Operation(summary = "판매자가 상품 등록", description = "판매자가 상품을 등록합니다.")
    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponseTemplate<ProductInfoResDto> productSave(@RequestPart("product") ProductSaveReqDto productSaveReqDto,
                                                              @RequestParam(value = "productImage", required = false) MultipartFile productImage,
                                                              Principal principal) throws IOException {
        ProductInfoResDto productInfoResDto = productService.productSave(productSaveReqDto, productImage, principal);
        return ApiResponseTemplate.successResponse(SuccessCode.PRODUCT_SAVE_SUCCESS, productInfoResDto);
    }

    @Operation(summary = "판매자가 상품 옵션 수정", description = "판매자가 상품 옵션 정보를 수정합니다.")
    @PatchMapping("/update")
    public ApiResponseTemplate<String> updateProductOption(@RequestBody ProductOptionUpdateReqDto reqDto) {
        productService.updateProductOption(reqDto);
        return ApiResponseTemplate.successWithNoContent(SuccessCode.PRODUCT_OPTION_UPDATE_SUCCESS);
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
    @DeleteMapping("/delete")
    public ApiResponseTemplate<String> productDelete(@RequestParam Long productId, Principal principal) {
        productService.productDelete(productId, principal);
        return ApiResponseTemplate.successWithNoContent(SuccessCode.PRODUCT_DELETE_SUCCESS);
    }
}
