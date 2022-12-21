package com.cfe.chat.controller;

import com.cfe.chat.controller.request.ChatMessageRequest;
import com.cfe.chat.controller.response.ChatMessageResponse;
import com.cfe.chat.dto.ActiveInfo;
import com.cfe.chat.dto.ChatMessage;
import com.cfe.chat.dto.CheckActiveRequest;
import com.cfe.chat.service.GroupService;
import com.cfe.chat.service.ChatService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@Controller
@AllArgsConstructor
public class ChatController {

    private final ChatService chatService;
//    private final GroupService groupService;
    private final SimpMessagingTemplate simpMessagingTemplate;

//    private static Map<Long, List<Long>> groupMembersInformationList = new HashMap<>();
//
//    static {
//        groupMembersInformationList.put(1L, List.of(1L,2L,3L));
//        groupMembersInformationList.put(2L, List.of(1L,4L,5L));
//    }

//    @SubscribeMapping

    @MessageMapping("/public/message") // to send public message, call this endpoint /app/public/message
    @SendTo("/public") // to receive public messages, subscribe to this topic from client /chatRoom/public
    public ChatMessage sendPublicMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

//    @MessageMapping("/group/message")
//    public ChatMessageResponse sendGroupMessage(@Payload ChatMessageRequest chatMessageRequest, SimpMessageHeaderAccessor headerAccessor) {
//        simpMessagingTemplate.convertAndSend("/group/" + groupId, chatMessage);
//        log.debug("Message Receive for group: {}", chatMessageRequest);
//        Long groupId = chatMessage.getGroupId();
//        if(groupId != null && chatMessage.getReceiverId() == null){
//            List<Long> members = groupMembersInformationList.get(groupId);
//            members.forEach(memberId -> {
//                chatMessage.setReceiverId(memberId);
//                simpMessagingTemplate.convertAndSendToUser(String.valueOf(memberId), "/private", chatMessage);
//                log.info("Message Processed: {}", chatMessage);
//            });
//        } else {
//            log.error("Invalid message for group chat: {}", chatMessage);
//        }
//        chatMessage.setReceiverId(null);
//        return chatService.sendMessage(chatMessageRequest);
//    }

    @MessageMapping("/private/message") // to send private message, call this endpoint /app/private/message
    public ChatMessageResponse sendPrivateMessage(@Payload ChatMessageRequest chatMessageRequest, SimpMessageHeaderAccessor headerAccessor) {
        log.debug("Message Receive: {}", chatMessageRequest);

         ChatMessageResponse chatMessageResponse = chatService.sendMessage(chatMessageRequest);
        // convertAndSendToUser will internally create mapping for /user himself from configuration
        // topic will be build like /user/{receiverName}/private
//        simpMessagingTemplate.convertAndSendToUser(String.valueOf(chatMessageRequest.getReceiverId()), "/private", chatMessageRequest);

//        ChatMessageResponse chatMessageResponse = ChatMessageResponse.builder()
//                .senderId(chatMessageRequest.getSenderId())
//                .receiverId(chatMessageRequest.getReceiverId())
//                .messageContent(chatMessageRequest.getMessageContent())
//                .messageType(chatMessageRequest.getMessageType())
//                .date(LocalDateTime.now().toString())
//                .build();

        log.info("Message Processed: {}", chatMessageResponse);
        return chatMessageResponse;
    }

    @PostMapping("/user/checkActive/{userId}")
    public List<ActiveInfo> checkActive(@RequestBody CheckActiveRequest checkActiveRequest){
        return chatService.checkActive(checkActiveRequest.getUserIds());
    }

    @GetMapping("/user/updateState/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateState(@PathVariable Long userId){
        chatService.updateState(userId);
    }


    @PostMapping("/test/private/message")
    public @ResponseBody ChatMessageResponse sendMessage(@RequestBody ChatMessageRequest chatMessageRequest){
        return chatService.sendMessage(chatMessageRequest);
    }

    @GetMapping({"/", "/home"})
    public @ResponseBody String sendMessage(){
        return "running";
    }

//    @MessageMapping("/addUser")
//    @SendTo("/user/public")
//    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
//        // Add username in web socket session
//        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("username", chatMessage.getSenderName());
//        return chatMessage;
//    }
}
