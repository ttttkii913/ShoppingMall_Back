package org.shoppingmall.notification.api.dto.request;

import org.shoppingmall.notification.domain.Notification;

import java.time.LocalDateTime;

public record NotificationReqDto(
        Long userId,
        String userName,
        String message // ex) ~님이 댓글을 남겼습니다
) {
    public Notification toEntity() {
        return Notification.builder()
                .userId(userId)
                .message(userName + message)
                .isRead(false)
                .notificationCreatedAt(LocalDateTime.now())
                .build();
    }
}
