package com.cfe.chat.listener;

import com.cfe.chat.dto.ChatMessage;
import com.cfe.chat.enums.MessageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Component
public class WebSocketEventListener {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.info("Received a new web socket connection: {}", event.toString());
        log.info("Source: {}", event.getSource().toString());
//        log.info("Message: {}", event.getMessage());
//        log.info("User: {}", event.getUser());
//
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//
//        String userId = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("username");
//
//        log.info("userId: {}", userId);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        log.info("Received a web socket dis-connection: {}", event.toString());
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

//        String userId = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("username");
//        if (userId != null) {
//            log.info("User Disconnected : " + userId);

//            ChatMessage chatMessage = ChatMessage.
//                    builder().messageType(MessageType.LEAVE).senderId(Long.valueOf(userId)).build();
//
//            messagingTemplate.convertAndSend("/topic/public", chatMessage);
//        }
    }
}
