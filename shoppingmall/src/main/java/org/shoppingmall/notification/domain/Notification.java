package org.shoppingmall.notification.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private long id;

    private Long userId;
    private String message;
    private boolean isRead;

    private LocalDateTime notificationCreatedAt;

    @PrePersist
    public void prePersist() {
        if (notificationCreatedAt == null) {
            notificationCreatedAt = LocalDateTime.now();
        }
    }
}
