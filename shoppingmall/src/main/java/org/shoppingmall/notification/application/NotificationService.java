package org.shoppingmall.notification.application;

import lombok.RequiredArgsConstructor;
import org.shoppingmall.common.EntityFinderException;
import org.shoppingmall.notification.api.dto.request.NotificationReqDto;
import org.shoppingmall.notification.api.dto.response.NotificationResDto;
import org.shoppingmall.notification.domain.Notification;
import org.shoppingmall.notification.domain.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final SseEmitters sseEmitters;
    private final EntityFinderException entityFinder;

    // 구독
    @Transactional(readOnly = true)
    public SseEmitter subscribe(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        SseEmitter emitter = new SseEmitter(60 * 60 * 1000L); // 1시간 유지
        sseEmitters.addEmitter(userId, emitter);

        try {
            emitter.send(SseEmitter.event().name("connect").data("connected"));
        } catch (IOException e) {
            emitter.completeWithError(e);
        }

        return emitter;
    }

    // 알림 생성 및 전송
    @Transactional
    public NotificationResDto createNotification(NotificationReqDto notificationReqDto) {
        Notification notification = notificationReqDto.toEntity();

        notificationRepository.save(notification);

        // SSE를 통해 클라이언트로 실시간 전송
        sseEmitters.sendToClient(notificationReqDto.userId(), NotificationResDto.from(notification));

        return NotificationResDto.from(notification);
    }

    // 알림 전체 목록 조회
    public List<NotificationResDto> getNotificationList(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        System.out.println("Fetching notifications for: " + userId);
        return notificationRepository.findByUserIdOrderByNotificationCreatedAtDesc(userId)
                .stream()
                .map(NotificationResDto::from)
                .toList();
    }

    // 알림 상세 조회 + 읽음 처리
    @Transactional
    public NotificationResDto readNotification(Long notificationId, Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        Notification notification = entityFinder.getNotificationById(notificationId);

        // 본인 알림만 접근 가능
        if (!notification.getUserId().equals(userId)) {
            throw new SecurityException("본인의 알림만 조회할 수 있습니다.");
        }

        // 읽음 처리
        try {
            var field = Notification.class.getDeclaredField("isRead");
            field.setAccessible(true);
            field.set(notification, true);
        } catch (Exception ignored) {}

        long totalCount = notificationRepository.countByUserId(userId);

        return NotificationResDto.from(notification, totalCount);
    }

    // 알림 삭제
    @Transactional
    public void deleteNotification(Long notificationId, Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        Notification notification = entityFinder.getNotificationById(notificationId);

        if (!notification.getUserId().equals(userId)) {
            throw new SecurityException("본인의 알림만 삭제할 수 있습니다.");
        }

        notificationRepository.delete(notification);
    }
}
