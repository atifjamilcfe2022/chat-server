package com.cfe.chat.service;

import com.cfe.chat.controller.request.MessageRequest;
import com.cfe.chat.domain.Message;
import com.cfe.chat.exception.DataNotFoundException;
import com.cfe.chat.repository.MessageRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

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
}
