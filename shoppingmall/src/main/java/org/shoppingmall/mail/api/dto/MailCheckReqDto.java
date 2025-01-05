package org.shoppingmall.mail.api.dto;

public record MailCheckReqDto(
        String email,
        String authNum
) {
}
