package org.shoppingmall.mail.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.shoppingmall.common.config.CommonApiResponse;
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
@Tag(name = "이메일 인증 API", description = "회원가입시 이메일 인증 발송, 검증 API")
@CommonApiResponse
public class MailController {

    private final MailService mailService;

    @Operation(summary = "회원가입시 이메일 인증 번호 발송", description = "회원가입시 사용자의 이메일 인증을 위해 인증 번호를 발송합니다.")
    @PostMapping("/send")
    public ApiResponseTemplate<SuccessCode> sendVerificationCode(@RequestParam String email) {
        try {
            // 인증번호 발송
            mailService.sendSimpleMessage(email);
            return ApiResponseTemplate.successWithNoContent(SuccessCode.EMAIL_SEND_SUCCESS);
        } catch (MessagingException e) {
            return ApiResponseTemplate.errorResponse(ErrorCode.INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
        }
    }

    @Operation(summary = "회원가입시 이메일 인증 번호 검증", description = "회원가입시 사용자의 이메일 인증 번호를 검증합니다.")
    @PostMapping("/check")
    public ApiResponseTemplate<SuccessCode> verifyCode(@RequestBody MailCheckReqDto mailCheckReqDto) {
        // 인증번호 검증
        boolean isVerified = mailService.verifyCode(mailCheckReqDto);

        if (isVerified) {
            return ApiResponseTemplate.successWithNoContent(SuccessCode.EMAIL_AUTH_SUCCESS);
        } else {
            return ApiResponseTemplate.errorResponse(ErrorCode.EMAIL_AUTH_FAIL, ErrorCode.EMAIL_AUTH_FAIL.getMessage());
        }
    }
}
