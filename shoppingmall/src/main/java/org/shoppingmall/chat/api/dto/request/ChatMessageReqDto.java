package org.shoppingmall.chat.api.dto.request;

public record ChatMessageReqDto(
        Long roomId,
        Long senderId,
        String senderType,
        String message
) {
}
