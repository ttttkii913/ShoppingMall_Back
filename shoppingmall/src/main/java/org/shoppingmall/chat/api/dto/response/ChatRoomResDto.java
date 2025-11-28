package org.shoppingmall.chat.api.dto.response;

import lombok.Builder;
import org.shoppingmall.chat.domain.ChatRoom;

import java.time.LocalDateTime;

@Builder
public record ChatRoomResDto(
        Long roomId,
        Long userId,
        Long adminId
) {
    public static ChatRoomResDto from(ChatRoom room) {
        return ChatRoomResDto.builder()
                .roomId(room.getRoomId())
                .userId(room.getUser() != null ? room.getUser().getId() : null)
                .adminId(room.getAdminId())
                .build();
    }
}
