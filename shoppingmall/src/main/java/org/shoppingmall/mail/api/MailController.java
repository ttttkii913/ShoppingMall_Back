package org.shoppingmall.mail.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.shoppingmall.common.error.ErrorCode;
import org.shoppingmall.mail.api.dto.MailCheckReqDto;
import org.shoppingmall.mail.application.MailService;
import org.shoppingmall.common.config.ApiResponseTemplate;
import org.shoppingmall.common.error.SuccessCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class MailController {

    private final MailService mailService;

    @Operation(summary = "회원가입시 이메일 인증 번호 발송", description = "회원가입시 사용자의 이메일 인증을 위해 인증 번호를 발송합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류. 관리자 문의.")
    })
    @PostMapping("/send")
    public ApiResponseTemplate<SuccessCode> sendVerificationCode(@RequestParam String email) {
        try {
            // 인증번호 발송
            mailService.sendSimpleMessage(email);
            return ApiResponseTemplate.successWithNoContent(SuccessCode.EMAIL_SEND_SUCCESS);
        } catch (MessagingException e) {
            return ApiResponseTemplate.errorResponse(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "회원가입시 이메일 인증 번호 검증", description = "회원가입시 사용자의 이메일 인증 번호를 검증합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류. 관리자 문의.")
    })
    @PostMapping("/check")
    public ApiResponseTemplate<SuccessCode> verifyCode(@RequestBody MailCheckReqDto mailCheckReqDto) {
        // 인증번호 검증
        boolean isVerified = mailService.verifyCode(mailCheckReqDto);

        if (isVerified) {
            return ApiResponseTemplate.successWithNoContent(SuccessCode.EMAIL_AUTH_SUCCESS);
        } else {
            return ApiResponseTemplate.errorResponse(ErrorCode.EMAIL_AUTH_FAIL);
        }
    }
}
