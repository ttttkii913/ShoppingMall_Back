package org.shoppingmall.chat.application;

import lombok.RequiredArgsConstructor;
import org.shoppingmall.chat.api.dto.response.ChatMessageResDto;
import org.shoppingmall.chat.api.dto.response.ChatRoomResDto;
import org.shoppingmall.chat.domain.ChatMessage;
import org.shoppingmall.chat.domain.ChatRoom;
import org.shoppingmall.chat.domain.repository.ChatMessageRepository;
import org.shoppingmall.chat.domain.repository.ChatRoomRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final SimpMessagingTemplate messagingTemplate;

    // 관리자 메세지 전송
    @Transactional
    public ChatMessage sendMessageFromAdmin(ChatMessage chatMessage, Principal adminPrincipal) {
        Long adminId = getAdminIdFromPrincipal(adminPrincipal);

        ChatMessage newMessage = ChatMessage.builder()
                .roomId(chatMessage.getRoomId())
                .senderId(adminId)
                .senderType("ADMIN")
                .message(chatMessage.getMessage())
                .createdAt(LocalDateTime.now())
                .build();

        ChatMessage savedMessage = chatMessageRepository.save(newMessage);

        // roomId 기준으로 구독중인 클라이언트에게 전송
        messagingTemplate.convertAndSend(
                "/sub/messages/" + chatMessage.getRoomId(),
                savedMessage
        );

        return savedMessage;
    }

    private Long getAdminIdFromPrincipal(Principal principal) {
        return 99L;
    }

    // 전체 목록 조회 - 관리자용
    @Transactional(readOnly = true)
    public List<ChatRoomResDto> getAllRoomsByAdmin() {
        List<ChatRoom> rooms = chatRoomRepository.findAll();
        return rooms.stream()
                .map(ChatRoomResDto::from)
                .toList();
    }

    // 상세 목록 조회 - 관리자용
    @Transactional(readOnly = true)
    public List<ChatMessageResDto> getMessagesByAdmin(Long roomId) {
        List<ChatMessage> messages = chatMessageRepository.findByRoomId(roomId);
        return messages.stream()
                .map(ChatMessageResDto::from)
                .toList();
    }
}
