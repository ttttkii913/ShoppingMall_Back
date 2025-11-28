package org.shoppingmall.chat.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.shoppingmall.chat.api.dto.response.ChatMessageResDto;
import org.shoppingmall.chat.api.dto.response.ChatRoomResDto;
import org.shoppingmall.chat.application.AdminChatService;
import org.shoppingmall.chat.domain.ChatMessage;
import org.shoppingmall.common.config.ApiResponseTemplate;
import org.shoppingmall.common.error.SuccessCode;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/admin/chat")
@RequiredArgsConstructor
@Tag(name = "관리자 채팅 API", description = "ADMIN CHATTING API")
public class AdminChatController {

    private final AdminChatService adminChatService;

    @Operation(summary = "관리자 채팅방 메세지 전송", description = "관리자가 채팅 메세지를 전송합니다.")
    @MessageMapping("admin/send")
    public ChatMessage sendMessage(ChatMessage chatMessage, Principal principal) {
        return adminChatService.sendMessageFromAdmin(chatMessage, principal);
    }

    @Operation(summary = "관리자 채팅방 목록 조회", description = "관리자가 모든 채팅 목록을 조회합니다.")
    @GetMapping("/rooms")
    public ApiResponseTemplate<List<ChatRoomResDto>> getAllRooms() {
        List<ChatRoomResDto> chatRoomResDtos = adminChatService.getAllRoomsByAdmin();
        return ApiResponseTemplate.successResponse(SuccessCode.GET_SUCCESS, chatRoomResDtos);
    }

    @Operation(summary = "관리자 채팅방 메세지 상세 조회", description = "관리자가 모든 채팅방의 상세 메세지를 조회합니다.")
    @GetMapping("/messages/{roomId}")
    public ApiResponseTemplate<List<ChatMessageResDto>> getMessages(@PathVariable Long roomId) {
        List<ChatMessageResDto> messages = adminChatService.getMessagesByAdmin(roomId);
        return ApiResponseTemplate.successResponse(SuccessCode.GET_SUCCESS, messages);
    }
}
