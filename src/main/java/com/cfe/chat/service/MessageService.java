package com.cfe.chat.service;

import com.cfe.chat.config.properties.ChatServerProperties;
import com.cfe.chat.controller.dto.LastChatMessageDto;
import com.cfe.chat.controller.request.MessageRequest;
import com.cfe.chat.domain.Message;
import com.cfe.chat.domain.UserMessage;
import com.cfe.chat.domain.custom.UserMessageHistory;
import com.cfe.chat.domain.views.LastChatMessage;
import com.cfe.chat.enums.MessageStatus;
import com.cfe.chat.exception.DataNotFoundException;
import com.cfe.chat.repository.LastChatMessageViewRepository;
import com.cfe.chat.repository.MessageRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MessageService {

    private final ChatServerProperties chatServerProperties;
    private final MessageRepository messageRepository;
    private final LastChatMessageViewRepository lastChatMessageViewRepository;
    private final UserMessageService userMessageService;

    private UserService userService;

    public List<Message> getMessages() {
        log.debug("Getting Chat Groups");

        List<Message> messages = messageRepository.findAll();

        log.info("messages found: {}", messages.size());
        return messages;
    }

    public Message getMessage(Long messageId) {
        log.debug("Getting message by Id: {}", messageId);

        Message message = messageRepository.findById(messageId).orElseThrow(
                () -> new DataNotFoundException("message not found with id: " + messageId));

        log.info("message found: {}", message);
        return message;
    }

    public Message addMessage(MessageRequest messageRequest) {
        log.debug("saving message: {}", messageRequest);
        Message message = Message.builder()
                .messageBody(messageRequest.getMessageBody())
                .active(Boolean.TRUE)
                .sender(userService.getUser(messageRequest.getUserId()))
                .build();
        messageRepository.save(message);
        log.info("Message saved. Id: {}", message.getId());
        return message;
    }

    public void updateMessage(MessageRequest messageRequest) {
        log.debug("saving message: {}", messageRequest);
        Message message = getMessage(messageRequest.getMessageId());
        message.setMessageBody(messageRequest.getMessageBody());
        message.setActive(Boolean.TRUE);
        message.setSender(userService.getUser(messageRequest.getUserId()));
        messageRepository.save(message);
        log.info("Message updated. Id: {}", message.getId());
    }

    public void deleteMessage(Long messageId) {
        log.debug("Deleting message: {}", messageId);

        Message message = getMessage(messageId);

        messageRepository.delete(message);
        log.info("message deleted: {}", message);
    }

    public void deleteMessage(Message message) {
        log.debug("Deleting message: {}", message);
        if (message != null) {
            messageRepository.delete(message);
            log.info("message deleted successfully");
        }
    }

    public List<Message> findByCreatedAt(ZonedDateTime createdAt) {
        log.debug("finding message by create date: {}", createdAt);
        List<Message> messages = messageRepository.findByCreatedAtBefore(createdAt);
        log.debug("found messages: {}", messages.size());
        return messages;
    }

    public List<LastChatMessageDto> getLastChatMessages(Long userId){
        log.debug("getting last messages with users");
        List<LastChatMessage> lastChatMessageList =
                lastChatMessageViewRepository.getLastChatMessages(userService.getUser(userId).getId());
        log.info("lastChatMessageList: {}", lastChatMessageList.size());

        List<LastChatMessageDto> lastChatMessageDtoList = new ArrayList<>();
        lastChatMessageList.forEach(lastChatMessage -> {
            LastChatMessageDto lastChatMessageDto = LastChatMessageDto.builder()
                    .id(lastChatMessage.getId())
                    .createdAt(lastChatMessage.getCreatedAt())
                    .updatedAt(lastChatMessage.getUpdatedAt())
                    .active(lastChatMessage.getActive())
                    .messageBody(lastChatMessage.getMessageBody())
                    .userId(lastChatMessage.getUserId())
                    .userName(lastChatMessage.getUserName())
                    .unreadMessageCount(userMessageService.getUnreadMessages(lastChatMessage.getUserId(), userId))
                    .build();
            lastChatMessageDtoList.add(lastChatMessageDto);
        });
        return lastChatMessageDtoList;
    }

//    public List<UserMessage> getUsersToWhomSendMessagesRecently(Long userId) {
//        List<UserMessage> userMessagesReceivedByOtherUser = userMessageService.getRecipientWhoReceiveMessageFromUser(userService.getUser(userId));
//        List<UserMessage> userMessagesSendByOtherUser = userMessageService.getSendersWhoSendMessageToUser(userService.getUser(userId));
//        List<LastMessagesWithUsers> lastMessagesWithUsers = new ArrayList<>();
//        userMessagesSendByOtherUser.forEach(userMessageWithUserAsReceiver -> {
//            lastMessagesWithUsers.add(LastMessagesWithUsers.builder()
//                            .id(userMessageWithUserAsReceiver.getMessage().getId())
//                            .fullName(userMessageWithUserAsReceiver.getReceiver().getFullName())
//                            .messageBody(userMessageWithUserAsReceiver.getMessage().getMessageBody())
//                            .createdAt(userMessageWithUserAsReceiver.getMessage().getCreatedAt())
//                    .build());
//        });
//        userMessagesReceivedByOtherUser.stream().filter(userMessageWithUserAsSender -> {
//            lastMessagesWithUsers.forEach(lastMessagesWithUsers1 -> {
//                if(userMessageWithUserAsSender.getMessage().getSender().getId().equals(lastMessagesWithUsers1.getId())){
//                    if(userMessageWithUserAsSender.getMessage().getCreatedAt().isAfter(lastMessagesWithUsers1.getCreatedAt())){
//                        lastMessagesWithUsers1.setId(userMessageWithUserAsSender.getMessage().getId());
//                        lastMessagesWithUsers1.setFullName(userMessageWithUserAsSender.getMessage().getSender().getFullName());
//                        lastMessagesWithUsers1.setMessageBody(userMessageWithUserAsSender.getMessage().getMessageBody());
//                        lastMessagesWithUsers1.setCreatedAt(userMessageWithUserAsSender.getMessage().getCreatedAt());
//                    }
//                }
//            });
//            if (lastMessagesWithUsers.getReceiver().getId()
//                    .equals(userMessageWithUserAsSender.getMessage().getId())) {
//                return true;
//            }
//            return false;
//        }).collect(Collectors.toList());
//    }

    public List<UserMessageHistory> userMessagesHistory(Long senderId, Long receiverId) {
        return userMessageService.userMessagesHistory(senderId, receiverId);
    }

    @Scheduled(cron = "${cron.expression.user}")
    public void deleteOldUserMessage() {
        log.debug("Cron job execution started for deleting user messages");
        long startTime = System.currentTimeMillis() / 1000;
        List<Message> messages = findByCreatedAt(ZonedDateTime.now().minusDays(chatServerProperties.getMessageHistoryDays()));
        if (!messages.isEmpty()) {
            List<UserMessage> userMessages = userMessageService.findByMessageIn(messages);
            if (!userMessages.isEmpty()) {
                userMessages.forEach(userMessage -> {
                    log.debug("deleting userMessage: {}", userMessage);
                    userMessageService.delete(userMessage);
                    log.info("userMessage deleted successfully");
                });
            }
            messages.forEach(this::deleteMessage);
        }
        long endTime = System.currentTimeMillis() / 1000;
        log.debug("Cron job execution ended. Total time taken: {} ", endTime - startTime);
    }

    public void updateUsersMessageStatus(Long userMessageId, MessageStatus messageStatus) {
        log.debug("updating userMessage: {} status: {}", userMessageId, messageStatus);
        userMessageService.updateUsersMessageStatus(userMessageId, messageStatus);
        log.info("UserMessages updated successfully");
    }

    public void markAllRead(Long senderId, Long receiverId) {
        userMessageService.markAllRead(senderId, receiverId);
    }

    //    public void updateUsersMessageStatus(Long userMessageId, MessageStatus messageStatus) {
//        log.debug("updating userMessage: {} status: {}", userMessageId, messageStatus);
//        UserMessage userMessage = getUserMessage(userMessageId);
//        userMessage.setMessageStatus(messageStatus);
//        userMessageRepository.save(userMessage);
//        log.info("UserMessages updated successfully");
//    }
}
