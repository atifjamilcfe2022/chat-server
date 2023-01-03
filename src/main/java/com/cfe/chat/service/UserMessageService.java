package com.cfe.chat.service;

import com.cfe.chat.config.properties.ChatServerProperties;
import com.cfe.chat.controller.request.UserMessageRequest;
import com.cfe.chat.domain.custom.UserMessageHistory;
import com.cfe.chat.domain.Message;
import com.cfe.chat.domain.UserMessage;
import com.cfe.chat.domain.User;
import com.cfe.chat.enums.MessageStatus;
import com.cfe.chat.exception.DataNotFoundException;
import com.cfe.chat.repository.UserMessageRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserMessageService {

    private final ChatServerProperties chatServerProperties;
    private final UserMessageRepository userMessageRepository;
//    private final MessageService messageService;
    private final UserService userService;

    public UserMessage addUserMessage(User user, Message message) {
        log.debug("Saving message recipient with details: user: {}, message: {}", user, message);
        UserMessage userMessage = UserMessage.builder()
                .message(message)
                .receiver(user)
                .active(Boolean.TRUE)
                .build();
        userMessageRepository.save(userMessage);
        log.info("message recipient saved. Id: {}", userMessage.getId());
        return userMessage;
    }

//    public List<UserMessage> findUserMessages() {
//        log.debug("Getting all userMessages");
//        List<UserMessage> userMessages = userMessageRepository.findAll();
//        log.info("Found {} userMessages", userMessages.size());
//        return userMessages;
//    }
//
//    public UserMessage getUserMessage(Long userMessageId) {
//        log.debug("Getting message recipient by userMessageRecipientId: {}", userMessageId);
//        UserMessage userMessage = userMessageRepository.findById(userMessageId)
//                .orElseThrow(() -> new DataNotFoundException("User Message not found with id: " + userMessageId));
//        log.info("Found UserMessage: {}", userMessage);
//        return userMessage;
//    }

//    public UserMessage addUserMessage(UserMessageRequest userMessageRequest) {
//        log.debug("saving userMessage: {}", userMessageRequest);
//        UserMessage userMessage = UserMessage.builder()
//                .messageStatus(userMessageRequest.getMessageStatus())
//                .active(Boolean.TRUE)
//                .message(messageService.getMessage(userMessageRequest.getMessageId()))
//                .receiver(userService.getUser(userMessageRequest.getUserId()))
//                .build();
//        userMessageRepository.save(userMessage);
//        log.info("saved UserMessage id: {}", userMessage.getId());
//        return userMessage;
//    }

//    public void updateUserMessage(UserMessageRequest userMessageRequest) {
//        log.debug("updating userMessage: {}", userMessageRequest);
//        UserMessage userMessage = getUserMessage(userMessageRequest.getUserMessageId());
//        userMessage.setMessageStatus(userMessageRequest.getMessageStatus());
//        userMessage.setActive(userMessageRequest.getActive());
//        userMessage.setMessage(messageService.getMessage(userMessageRequest.getMessageId()));
//        userMessage.setReceiver(userService.getUser(userMessageRequest.getUserId()));
//        userMessageRepository.save(userMessage);
//        log.info("updated UserMessage id: {}", userMessage.getId());
//    }

//    public void deleteUserMessage(Long userMessageId) {
//        log.debug("deleting userMessage by id: {}", userMessageId);
//        UserMessage userMessage = getUserMessage(userMessageId);
//        userMessageRepository.delete(userMessage);
//        log.info("deleted UserMessage id: {}", userMessage.getId());
//    }

    public List<UserMessageHistory> userMessagesHistory(Long senderId, Long receiverId) {
        log.debug("finding userMessage history by sender: {} and receiver: {}", senderId, receiverId);
        OffsetDateTime dateTime = OffsetDateTime.now().minusDays(chatServerProperties.getMessageHistoryDays());
        List<UserMessageHistory> userMessageHistories =
                userMessageRepository.findUserMessageHistory(
                        userService.getUser(senderId), userService.getUser(receiverId), dateTime);
        log.info("found: {} UserMessages", userMessageHistories.size());
        return userMessageHistories;
    }

//    public void updateUsersMessageStatus(Long userMessageId, MessageStatus messageStatus) {
//        log.debug("updating userMessage: {} status: {}", userMessageId, messageStatus);
//        UserMessage userMessage = getUserMessage(userMessageId);
//        userMessage.setMessageStatus(messageStatus);
//        userMessageRepository.save(userMessage);
//        log.info("UserMessages updated successfully");
//    }

    public List<UserMessage> getUsersWithChatMadeRecently(User user) {
        log.debug("Getting all users who chat with user: {} recently", user);
        List<UserMessage> userMessages = userMessageRepository.findRecipientChatWithUser(user);
        log.info("Found {} userMessages", userMessages);
        return userMessages;
    }

    public List<UserMessage> findByMessageIn(List<Message> messages) {
        return userMessageRepository.findByMessageIn(messages);
    }

    public void delete(UserMessage userMessage) {
        userMessageRepository.delete(userMessage);
    }
}
