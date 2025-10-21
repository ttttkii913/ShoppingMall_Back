package org.shoppingmall.notification.api.dto.response;

import lombok.Builder;
import org.shoppingmall.notification.domain.Notification;

import java.time.LocalDateTime;

@Builder
public record NotificationResDto(
        Long notificationId,
        Long userId,
        String message,
        boolean isRead,
        LocalDateTime notificationCreatedAt,
        Long totalCount
) {
    public static NotificationResDto from(Notification notification, Long totalCount) {
        return NotificationResDto.builder()
                .notificationId(notification.getId())
                .userId(notification.getUserId())
                .message(notification.getMessage())
                .isRead(notification.isRead())
                .notificationCreatedAt(notification.getNotificationCreatedAt())
                .totalCount(totalCount)
                .build();
    }

    public static NotificationResDto from(Notification notification) {
        return from(notification, null);
    }
}
