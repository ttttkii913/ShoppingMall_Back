package org.shoppingmall.chat.api.dto.response;

import lombok.Builder;
import org.shoppingmall.chat.domain.ChatMessage;

@Builder
public record ChatMessageResDto(
        Long messageId,
        Long roomId,
        Long senderId,
        String senderType,
        String message,
        String createdAt
) {
    public static ChatMessageResDto from(ChatMessage msg) {
        return ChatMessageResDto.builder()
                .messageId(msg.getMessageId())
                .roomId(msg.getRoomId())
                .senderId(msg.getSenderId())
                .senderType(msg.getSenderType())
                .message(msg.getMessage())
                .createdAt(msg.getCreatedAt() != null ? msg.getCreatedAt().toString() : null)
                .build();
    }
}
