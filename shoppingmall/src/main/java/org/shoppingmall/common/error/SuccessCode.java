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
    LOCATION_UPDATE_SUCCESS("위치가 성공적으로 수정되었습니다."),
    CURATION_UPDATE_SUCCESS("큐레이션이 성공적으로 수정되었습니다."),
    COMMENT_UPDATE_SUCCESS("댓글이 성공적으로 수정되었습니다."),

    LOCATION_DELETE_SUCCESS("위치가 성공적으로 삭제되었습니다."),
    CURATION_DELETE_SUCCESS( "큐레이션이 성공적으로 삭제되었습니다."),
    COMMENT_DELETE_SUCCESS("댓글이 성공적으로 삭제되었습니다."),
    LIKE_DELETE_SUCCESS("좋아요가 성공적으로 삭제되었습니다."),

    USER_SIGNUP_SUCCESS("회원가입에 성공하였습니다."),
    USER_LOGIN_SUCCESS("로그인에 성공하였습니다."),
    USER_INFO_UPDATE_SUCCESS("사용자의 정보가 성공적으로 변경되었습니다."),

    EMAIL_SEND_SUCCESS("이메일 전송에 성공하였습니다."),
    /**
     * 201 CREATED (POST의 결과 상태)
     */
    LOCATION_SAVE_SUCCESS("위치가 성공적으로 등록되었습니다."),
    CURATION_SAVE_SUCCESS("큐레이션이 성공적으로 등록되었습니다."),
    COMMENT_SAVE_SUCCESS("댓글이 성공적으로 저장되었습니다."),
    LIKE_SAVE_SUCCESS("좋아요가 성공적으로 저장되었습니다."),

    EMAIL_AUTH_SUCCESS("이메일 인증에 성공하였습니다.");


    private final String message;

}
