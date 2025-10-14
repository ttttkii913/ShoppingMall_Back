package org.shoppingmall.common.error;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

    /**
     * 404 NOT FOUND
     */
    CART_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND,"장바구니가 없습니다.", "NOT_FOUND_404"),
    CARTITEM_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND,"장바구니 상품이 없습니다.", "NOT_FOUND_404"),
    ORDER_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND,"주문이 없습니다.", "NOT_FOUND_404"),
    USER_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND,"해당 사용자가 없습니다.", "NOT_FOUND_404"),
    FILE_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND,"파일을 찾을 수 없습니다.", "NOT_FOUND_404"),
    PRODUCT_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND,"상품을 찾을 수 없습니다.", "NOT_FOUND_404"),
    CATEGORY_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND,"카테고리를 찾을 수 없습니다.", "NOT_FOUND_404"),
    REVIEW_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND,"리뷰를 찾을 수 없습니다.", "NOT_FOUND_404"),
    COMMENT_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND,"댓글을 찾을 수 없습니다.", "NOT_FOUND_404"),
    LIKE_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND,"공감을 누르지 않은 상품입니다.", "NOT_FOUND_404"),
    PRODUCT_OPTION_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND,"상품 옵션을 찾을 수 없습니다", "NOT_FOUND_404"),
    SELLER_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND,"판매자를 찾을 수 없습니다", "NOT_FOUND_404"),
    NO_USER_LIKE_PRODUCT_EXCEPTION(HttpStatus.NOT_FOUND,"아직 공감을 누른 상품이 없습니다.", "NOT_FOUND_404"),

    // 401 UNAUTHORIZED
    NO_AUTHORIZATION_EXCEPTION(HttpStatus.UNAUTHORIZED,"권한이 없습니다.", "UNAUTHORIZED_401"),

    /**
     * 500 INTERNAL SERVER ERROR
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"알 수 없는 서버 에러가 발생했습니다.", "INTERNAL_SERVER_ERROR_500"),

    // 403 FORBIDDEN
    FORBIDDEN_EXCEPTION(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.", "FORBIDDEN_403"),

    /**
     * 400 BAD REQUEST
     */
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "유효성 검사에 실패하였습니다.", "BAD_REQUEST_400"),
    ALREADY_SELLER(HttpStatus.BAD_REQUEST, "이미 존재하는 판매자입니다.", "BAD_REQUEST_400"),
    ALREADY_LIKE_PRODUCT(HttpStatus.BAD_REQUEST,"이미 공감을 누른 상품입니다.", "BAD_REQUEST_400"),
    EMAIL_AUTH_FAIL(HttpStatus.BAD_REQUEST,"이메일 인증에 실패하였습니다.", "BAD_REQUEST_400"),
    PASSWORD_MATCH_FAIL(HttpStatus.BAD_REQUEST,"비밀번호 일치에 실패하였습니다.", "BAD_REQUEST_400"),
    NOT_CHILD_COMMENT_HIERARCHY(HttpStatus.BAD_REQUEST,"대댓글의 대댓글은 허용하지 않습니다. BAD_REQUEST_400", "BAD_REQUEST_400");

    private final HttpStatus httpStatus;
    private final String message;
    private final String code;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}