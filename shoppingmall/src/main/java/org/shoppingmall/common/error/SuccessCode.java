package org.shoppingmall.common.error;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessCode {   // Success 상태와 메세지, 코드를 정의
    /**
     * 200 OK   (요청 성공
     */
    GET_SUCCESS("성공적으로 조회했습니다."),
    PRODUCT_UPDATE_SUCCESS("상품이 성공적으로 수정되었습니다."),
    CART_UPDATE_SUCCESS("장바구니가 성공적으로 수정되었습니다."),
    REVIEW_UPDATE_SUCCESS("리뷰가 성공적으로 수정되었습니다."),
    USER_INFO_UPDATE_SUCCESS("사용자의 정보가 성공적으로 변경되었습니다."),

    PRODUCT_DELETE_SUCCESS("상품이 성공적으로 삭제되었습니다."),
    CART_DELETE_SUCCESS( "장바구니가 성공적으로 삭제되었습니다."),
    REVIEW_DELETE_SUCCESS("리뷰가 성공적으로 삭제되었습니다."),
    ORDER_DELETE_SUCCESS("주문이 성공적으로 삭제되었습니다."),
    LIKE_DELETE_SUCCESS("공감이 성공적으로 삭제되었습니다."),

    USER_SIGNUP_SUCCESS("회원가입에 성공하였습니다."),
    USER_LOGIN_SUCCESS("로그인에 성공하였습니다."),
    EMAIL_SEND_SUCCESS("이메일 전송에 성공하였습니다."),

    /**
     * 201 CREATED (POST의 결과 상태)
     */
    PRODUCT_SAVE_SUCCESS("상품이 성공적으로 등록되었습니다."),
    CART_SAVE_SUCCESS("장바구니에 성공적으로 등록되었습니다."),
    ORDER_SAVE_SUCCESS("주문이 성공적으로 저장되었습니다."),
    REVIEW_SAVE_SUCCESS("리뷰가 성공적으로 저장되었습니다."),
    EMAIL_AUTH_SUCCESS("이메일 인증에 성공하였습니다."),
    LIKE_SAVE_SUCCESS("공감이 성공적으로 저장되었습니다.");

    private final String message;

}
