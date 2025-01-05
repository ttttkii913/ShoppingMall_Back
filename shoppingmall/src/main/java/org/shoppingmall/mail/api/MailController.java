package org.shoppingmall.mail.api;

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

    // 인증번호 발송
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

    // 인증번호 확인
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
