package com.cfe.chat.service;

import com.cfe.chat.config.properties.ChatServerProperties;
import com.cfe.chat.controller.request.ChatMessageRequest;
import com.cfe.chat.controller.request.MessageRequest;
import com.cfe.chat.controller.response.ChatMessageResponse;
import com.cfe.chat.domain.*;
import com.cfe.chat.dto.ActiveInfo;
import com.cfe.chat.enums.MessageStatus;
import com.cfe.chat.exception.InvalidDataUpdateException;
import com.cfe.chat.utils.ChatServerConstants;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ChatService {

    private final ChatServerProperties chatServerProperties;
    private final UserService userService;

    private final GroupService groupService;
    private final MessageService messageService;
    private final UserMessageService userMessageService;
    private final UserGroupMessageService userGroupMessageService;
    private final UserGroupService userGroupService;

    private final SimpMessagingTemplate simpMessagingTemplate;

    public List<ActiveInfo> checkActive(@NonNull List<Long> userIds) {
        log.info("checking active user for list: {}", userIds);
        List<ActiveInfo> activeInfos = new ArrayList<>();
        userIds.forEach(userId -> {
            ActiveInfo activeInfo = ChatServerConstants.activeInfoMap.get(userId);
            if (activeInfo != null) {
                if (Duration.between(OffsetDateTime.now(), activeInfo.getPingTime())
                        .get(ChronoUnit.SECONDS) < Long.parseLong(chatServerProperties.getTimeToLive())) {
                    activeInfo.setAlive(Boolean.TRUE);
                }
            } else {
                activeInfo = ActiveInfo.builder().userId(userId).alive(Boolean.FALSE).pingTime(null).build();
            }
            activeInfos.add(activeInfo);
        });
        return activeInfos;
    }

//    public void updateState(Long userId) {
//        ActiveInfo activeInfo = ChatServerConstants.activeInfoMap.get(userId);
//        if (activeInfo != null) {
//            activeInfo.setPingTime(OffsetDateTime.now());
//            ChatServerConstants.activeInfoMap.replace(userId, activeInfo);
//        } else {
//            activeInfo = ActiveInfo.builder().userId(userId).pingTime(OffsetDateTime.now()).build();
//            ChatServerConstants.activeInfoMap.put(userId, activeInfo);
//        }
//    }

    @Transactional
    public ChatMessageResponse sendMessage(ChatMessageRequest chatMessageRequest) {
        log.info("processing Chat Message: {}", chatMessageRequest);

        ChatMessageResponse chatMessageResponse = ChatMessageResponse.builder().build();

        MessageRequest messageRequest = MessageRequest.builder()
                .messageBody(chatMessageRequest.getMessageContent())
                .messageStatus(MessageStatus.INITIATED)
                .userId(chatMessageRequest.getSenderId())
                .active(Boolean.TRUE)
                .build();
        Message message = messageService.addMessage(messageRequest);

        chatMessageResponse.setMessageType(chatMessageRequest.getMessageType());
        chatMessageResponse.setMessageContent(chatMessageRequest.getMessageContent());
        chatMessageResponse.setDate(OffsetDateTime.now().toString());
        chatMessageResponse.setSenderId(chatMessageRequest.getSenderId());

        if (chatMessageRequest.getReceiverId() != null) {
            UserMessage userMessage = userMessageService.addUserMessage(userService.getUser(chatMessageRequest.getReceiverId()), message);
            chatMessageRequest.setUserMessageId(userMessage.getId());
            chatMessageResponse.setReceiverId(chatMessageRequest.getReceiverId());

            simpMessagingTemplate.convertAndSendToUser(String.valueOf(chatMessageRequest.getReceiverId()), "/private", chatMessageRequest);
        } else if (chatMessageRequest.getGroupId() != null) {
            Group group = groupService.getGroup(chatMessageRequest.getGroupId());
            List<User> users = userGroupService.findUsersInGroup(group);
            if(!users.isEmpty()) {
                for(User user : users){
                    if(!user.getId().equals(chatMessageRequest.getSenderId())){
                        UserGroup userGroup = userGroupService.findByUserGroup(user, group);
                        UserGroupMessage userGroupMessage = userGroupMessageService.addUserGroupMessage(userGroup, message);
                        chatMessageRequest.setUserGroupMessageId(userGroupMessage.getId());
                        simpMessagingTemplate.convertAndSendToUser(String.valueOf(user.getId()), "/private", chatMessageRequest);
                    }
                }
            }
            chatMessageResponse.setGroupId(chatMessageRequest.getGroupId());
        } else {
            throw new InvalidDataUpdateException("Invalid Request. No recipient or recipient group found");
        }
        chatMessageResponse.setMessageId(message.getId());
        return chatMessageResponse;
    }
}
