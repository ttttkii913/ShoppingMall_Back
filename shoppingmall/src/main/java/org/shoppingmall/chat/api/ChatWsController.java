package org.shoppingmall.chat.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shoppingmall.chat.api.dto.request.ChatMessageReqDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatWsController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.send")
    public void sendMessage(ChatMessageReqDto msg) {

        messagingTemplate.convertAndSend(
                "/sub/chat/user/" + msg.senderId(),
                msg
        );

        messagingTemplate.convertAndSend(
                "/sub/chat/admin/" + msg.roomId(),
                msg
        );

        log.info("room={}, msg={}", msg.roomId(), msg.message() );
    }
}
