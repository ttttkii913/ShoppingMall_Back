package org.shoppingmall.chat.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.shoppingmall.chat.api.dto.response.ChatMessageResDto;
import org.shoppingmall.chat.api.dto.response.ChatRoomResDto;
import org.shoppingmall.chat.application.ChatService;
import org.shoppingmall.chat.domain.ChatMessage;
import org.shoppingmall.common.config.ApiResponseTemplate;
import org.shoppingmall.common.error.SuccessCode;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Tag(name = "채팅 API", description = "CHATTING API")
public class ChatController {

    private final ChatService chatService;

    @Operation(summary = "채팅방 생성", description = "로그인한 사용자가 상담원과의 채팅방을 생성합니다.")
    @PostMapping("/rooms")
    public ApiResponseTemplate<ChatRoomResDto> createRoom(Principal principal) {
        ChatRoomResDto chatRoomResDto = chatService.createRoom(principal);
        return ApiResponseTemplate.successResponse(SuccessCode.GET_SUCCESS, chatRoomResDto);
    }

    @Operation(summary = "채팅 메세지 전송", description = "로그인한 사용자가 채팅방에 메세지를 전송합니다.")
    @MessageMapping("/send")
    public void sendMessage(ChatMessage chatMessage, Principal principal) {
        chatService.sendMessage(chatMessage, principal);
    }

    @Operation(summary = "채팅방 목록 조회", description = "로그인한 사용자가 자신의 채팅 목록 리스트를 전체 조회합니다.")
    @GetMapping("/rooms")
    public ApiResponseTemplate<List<ChatRoomResDto>> getAllRooms(Principal principal) {
        List<ChatRoomResDto> chatRoomResDtos = chatService.getAllRooms(principal);
        return ApiResponseTemplate.successResponse(SuccessCode.GET_SUCCESS, chatRoomResDtos);
    }

    @Operation(summary = "채팅방 메세지 상세 조회", description = "로그인한 사용자가 방ID에 따른 자신의 채팅 메세지를 조회합니다.")
    @GetMapping("/messages/{roomId}")
    public ApiResponseTemplate<List<ChatMessageResDto>> getMessages(@PathVariable Long roomId, Principal principal) {
        List<ChatMessageResDto> messages = chatService.getMessages(roomId, principal);
        return ApiResponseTemplate.successResponse(SuccessCode.GET_SUCCESS, messages);
    }
}
