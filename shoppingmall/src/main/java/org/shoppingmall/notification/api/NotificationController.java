package org.shoppingmall.notification.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.shoppingmall.common.config.ApiResponseTemplate;
import org.shoppingmall.common.config.CommonApiResponse;
import org.shoppingmall.common.error.SuccessCode;
import org.shoppingmall.notification.api.dto.request.NotificationReqDto;
import org.shoppingmall.notification.api.dto.response.NotificationResDto;
import org.shoppingmall.notification.application.NotificationService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
@CommonApiResponse
@Tag(name = "알림 API", description = "알림 관련 API")
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "알림 수신 연결", description = "알림 수신 연결")
    @GetMapping(value = "/subscribe/{userId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(Principal principal) {
        return notificationService.subscribe(principal);
    }

    @Operation(summary = "알림 생성", description = "알림 생성 및 전송")
    @PostMapping
    public ApiResponseTemplate<NotificationResDto> createNotification(@RequestBody NotificationReqDto notificationReqDto) {
        NotificationResDto resDto = notificationService.createNotification(notificationReqDto);
        return ApiResponseTemplate.successResponse(SuccessCode.NOTIFICATION_SAVE_SUCCESS, resDto);
    }

    @Operation(summary = "알림 전체 목록 조회", description = "사용자가 받은 알림 전체 목록을 조회합니다.")
    @GetMapping("/all")
    public ApiResponseTemplate<List<NotificationResDto>> getNotifications(Principal principal) {
        List<NotificationResDto> notificationResDto = notificationService.getNotificationList(principal);
        return ApiResponseTemplate.successResponse(SuccessCode.GET_SUCCESS,notificationResDto);
    }

    @Operation(summary = "알림 상세 조회 및 읽음 처리", description = "사용자가 받은 알림 상세 정보를 조회합니다. (+읽음 처리) ")
    @GetMapping("/detail/{notificationId}")
    public ApiResponseTemplate<NotificationResDto> getNotificationDetail(@PathVariable("notificationId") Long notificationId, Principal principal) {
        NotificationResDto notificationResDto = notificationService.readNotification(notificationId, principal);
        return ApiResponseTemplate.successResponse(SuccessCode.GET_SUCCESS, notificationResDto);
    }

    @Operation(summary = "알림 삭제", description = "사용자의 알림 목록에서 특정 알림을 삭제합니다.")
    @DeleteMapping("/detail/{notificationId}")
    public ApiResponseTemplate<String> deleteNotification(@PathVariable("notificationId") Long notificationId, Principal principal) {
        notificationService.deleteNotification(notificationId, principal);
        return ApiResponseTemplate.successWithNoContent(SuccessCode.NOTIFICATION_DELETE_SUCCESS);
    }
}
