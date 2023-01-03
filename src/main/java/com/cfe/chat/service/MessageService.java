package com.cfe.chat.service;

import com.cfe.chat.config.properties.ChatServerProperties;
import com.cfe.chat.controller.request.MessageRequest;
import com.cfe.chat.domain.Message;
import com.cfe.chat.domain.UserMessage;
import com.cfe.chat.domain.custom.UserMessageHistory;
import com.cfe.chat.exception.DataNotFoundException;
import com.cfe.chat.repository.MessageRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MessageService {

    private final ChatServerProperties chatServerProperties;
    private final MessageRepository messageRepository;
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
        if(message != null) {
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

    public List<UserMessage> getUsersToWhomSendMessagesRecently(Long userId) {
        return userMessageService.getUsersWithChatMadeRecently(userService.getUser(userId));
    }

    public List<UserMessageHistory> userMessagesHistory(Long senderId, Long receiverId) {
        return userMessageService.userMessagesHistory(senderId, receiverId);
    }

    @Scheduled(cron = "${cron.expression.user}")
    public void deleteOldUserMessage() {
        log.debug("Cron job execution started for deleting user messages");
        long startTime = System.currentTimeMillis() / 1000;
        List<Message> messages = findByCreatedAt(ZonedDateTime.now().minusDays(chatServerProperties.getMessageHistoryDays()));
        if(!messages.isEmpty()) {
            List<UserMessage> userMessages = userMessageService.findByMessageIn(messages);
            if(!userMessages.isEmpty()){
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
}
