package org.shoppingmall.chat.application;

import lombok.RequiredArgsConstructor;
import org.shoppingmall.chat.api.dto.response.ChatMessageResDto;
import org.shoppingmall.chat.api.dto.response.ChatRoomResDto;
import org.shoppingmall.chat.domain.ChatRoom;
import org.shoppingmall.chat.domain.ChatMessage;
import org.shoppingmall.chat.domain.repository.ChatMessageRepository;
import org.shoppingmall.chat.domain.repository.ChatRoomRepository;
import org.shoppingmall.common.EntityFinderException;
import org.shoppingmall.common.error.ErrorCode;
import org.shoppingmall.common.exception.CustomException;
import org.shoppingmall.user.domain.User;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final EntityFinderException entityFinderException;
    private final SimpMessagingTemplate messagingTemplate;

    // 채팅방 생성
    @Transactional
    public ChatRoomResDto createRoom(Principal principal) {
        User user = entityFinderException.getUserFromPrincipal(principal);

        ChatRoom room = ChatRoom.builder()
                .user(user)
                .adminId(99L)
                .build();

        ChatRoom savedRoom = chatRoomRepository.save(room);

        return ChatRoomResDto.from(savedRoom);
    }

    // 채팅 메세지 전송
    @Transactional
    public ChatMessage sendMessage(ChatMessage chatMessage, Principal principal) {
        User user = entityFinderException.getUserFromPrincipal(principal);

        ChatMessage newMessage = ChatMessage.builder()
                .roomId(chatMessage.getRoomId())
                .senderId(user.getId())
                .senderType("USER")
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

    // 로그인 한 사용자의 채팅 전체 목록 조회
    @Transactional(readOnly = true)
    public List<ChatRoomResDto> getAllRooms(Principal principal) {
        User user = entityFinderException.getUserFromPrincipal(principal);
        List<ChatRoom> rooms = chatRoomRepository.findAllByUser(user);
        return rooms.stream()
                .map(ChatRoomResDto::from)
                .toList();
    }

    // 로그인 한 사용자의 특정 채팅방 메시지 상세 조회
    @Transactional(readOnly = true)
    public List<ChatMessageResDto> getMessages(Long roomId, Principal principal) {
        User user = entityFinderException.getUserFromPrincipal(principal);
        ChatRoom chatRoom = entityFinderException.getChatRoomById(roomId);

        // 본인 채팅 아니면 접근 불가 예외
        if (!chatRoom.getUser().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.NO_AUTHORIZATION_EXCEPTION,
                    ErrorCode.NO_AUTHORIZATION_EXCEPTION.getMessage());
        }

        List<ChatMessage> messages = chatMessageRepository.findByRoomId(roomId);

        return messages.stream()
                .map(ChatMessageResDto::from)
                .toList();
    }
}
