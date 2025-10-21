package org.shoppingmall.notification.api.dto.request;

import org.shoppingmall.notification.domain.Notification;

import java.time.LocalDateTime;

public record NotificationReqDto(
        Long userId,
        String message
) {
    public Notification toEntity() {
        return Notification.builder()
                .userId(userId)
                .message(message)
                .isRead(false)
                .notificationCreatedAt(LocalDateTime.now())
                .build();
    }
}
