package org.shoppingmall.common.error;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessCode {   // Success 상태와 메세지, 코드를 정의
    /**
     * 200 OK   (요청 성공
     */
    GET_SUCCESS(HttpStatus.OK, "성공적으로 조회했습니다."),
    PRODUCT_UPDATE_SUCCESS(HttpStatus.OK, "상품이 성공적으로 수정되었습니다."),
    PRODUCT_OPTION_UPDATE_SUCCESS(HttpStatus.OK, "상품 옵션이 성공적으로 수정되었습니다."),
    CART_UPDATE_SUCCESS(HttpStatus.OK, "장바구니가 성공적으로 수정되었습니다."),
    REVIEW_UPDATE_SUCCESS(HttpStatus.OK, "리뷰가 성공적으로 수정되었습니다."),
    COMMENT_UPDATE_SUCCESS(HttpStatus.OK, "댓글이 성공적으로 수정되었습니다."),
    USER_INFO_UPDATE_SUCCESS(HttpStatus.OK, "사용자의 정보가 성공적으로 변경되었습니다."),
    USER_EMAIL_FIND_SUCCESS(HttpStatus.OK, "사용자의 이메일 정보를 성공적으로 조회하였습니다."),
    USER_PASSWORD_RESET_SUCCESS(HttpStatus.OK, "사용자의 비밀번호를 성공적으로 변경하였습니다."),

    PRODUCT_DELETE_SUCCESS(HttpStatus.OK, "상품이 성공적으로 삭제되었습니다."),
    CART_DELETE_SUCCESS( HttpStatus.OK, "장바구니가 성공적으로 삭제되었습니다."),
    REVIEW_DELETE_SUCCESS(HttpStatus.OK, "리뷰가 성공적으로 삭제되었습니다."),
    COMMENT_DELETE_SUCCESS(HttpStatus.OK, "댓글이 성공적으로 삭제되었습니다."),
    ORDER_DELETE_SUCCESS(HttpStatus.OK, "주문이 성공적으로 삭제되었습니다."),
    LIKE_DELETE_SUCCESS(HttpStatus.OK, "공감이 성공적으로 삭제되었습니다."),

    USER_SIGNUP_SUCCESS(HttpStatus.OK, "회원가입에 성공하였습니다."),
    USER_LOGIN_SUCCESS(HttpStatus.OK, "로그인에 성공하였습니다."),
    EMAIL_SEND_SUCCESS(HttpStatus.OK, "이메일 전송에 성공하였습니다."),
    MEMBER_INFO_DELETE(HttpStatus.OK, "회원 탈퇴에 성공하였습니다."),
    /**
     * 201 CREATED (POST의 결과 상태)
     */
    REFRESH_TOKEN_SUCCESS(HttpStatus.CREATED, "리프레시 토큰으로 액세스 토큰 재발급에 성공하였습니다."),
    PRODUCT_SAVE_SUCCESS(HttpStatus.CREATED, "상품이 성공적으로 등록되었습니다."),
    CART_SAVE_SUCCESS(HttpStatus.CREATED, "장바구니에 성공적으로 등록되었습니다."),
    ORDER_SAVE_SUCCESS(HttpStatus.CREATED, "주문이 성공적으로 저장되었습니다."),
    REVIEW_SAVE_SUCCESS(HttpStatus.CREATED, "리뷰가 성공적으로 저장되었습니다."),
    COMMENT_SAVE_SUCCESS(HttpStatus.CREATED,"댓글이 성공적으로 저장되었습니다."),
    EMAIL_AUTH_SUCCESS(HttpStatus.CREATED,"이메일 인증에 성공하였습니다."),
    LIKE_SAVE_SUCCESS(HttpStatus.CREATED,"공감이 성공적으로 저장되었습니다."),
    SELLER_REGISTER_SUCCESS(HttpStatus.CREATED, "판매자 정보가 등록되었습니다. 관리자의 승인 필요"),
    SELLER_APPROVE_SUCCESS(HttpStatus.CREATED, "판매자 정보가 승인되었습니다."),
    SELLER_REJECT_SUCCESS(HttpStatus.CREATED, "판매자 정보가 거절되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}