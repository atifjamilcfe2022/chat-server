package com.cfe.chat.service;

import com.cfe.chat.config.properties.ChatServerProperties;
import com.cfe.chat.domain.custom.GroupMessageHistory;
import com.cfe.chat.domain.*;
import com.cfe.chat.enums.MessageStatus;
import com.cfe.chat.exception.DataNotFoundException;
import com.cfe.chat.repository.UserGroupMessageRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserGroupMessageService {

    private final ChatServerProperties chatServerProperties;
    private final UserGroupMessageRepository userGroupMessageRepository;
    private final UserGroupService userGroupService;
    private final MessageService messageService;

    public UserGroupMessage getUserGroupMessage(Long userGroupMessageId){
        log.debug("Getting userGroupMessage by Id: {}", userGroupMessageId);
        UserGroupMessage userGroupMessage = userGroupMessageRepository.findById(userGroupMessageId)
                .orElseThrow(() -> new DataNotFoundException("userGroupMessage not found with id: " + userGroupMessageId));
        log.info("Found userGroupMessage: {}", userGroupMessage);
        return userGroupMessage;
    }

    public UserGroupMessage addUserGroupMessage(UserGroup userGroup, Message message) {
        UserGroupMessage userGroupMessage = UserGroupMessage.builder()
                .messageStatus(MessageStatus.INITIATED)
                .active(Boolean.TRUE)
                .userGroup(userGroup)
                .message(message)
                .build();
        userGroupMessageRepository.save(userGroupMessage);
        return userGroupMessage;
    }

    public List<GroupMessageHistory> messagesHistoryByGroup(Long groupId) {
        log.debug("finding userGroupMessages history by group: {}", groupId);
        OffsetDateTime dateTime = OffsetDateTime.now().minusDays(chatServerProperties.getMessageHistoryDays());
        List<UserGroup> userGroups = userGroupService.findUsersInGroup(groupId);
        List<GroupMessageHistory> groupMessageHistories =
                userGroupMessageRepository.findUserMessageHistory(userGroups, dateTime);
        log.info("found: {} UserGroupMessages", groupMessageHistories.size());
        return groupMessageHistories;
    }

    public void updateStatusOfUserGroupMessage(Long userGroupMessageId, MessageStatus messageStatus) {
        log.debug("updating userGroupMessages status: {} of group: {}", messageStatus, userGroupMessageId);
        UserGroupMessage userGroupMessage = getUserGroupMessage(userGroupMessageId);
        userGroupMessage.setMessageStatus(messageStatus);
        userGroupMessageRepository.save(userGroupMessage);
        log.info("updated userGroupMessages status");
    }

    @Scheduled(cron = "${cron.expression.group}")
    public void deleteOldGroupMessage() {
        log.debug("Cron job execution started for deleting group messages");
        long startTime = System.currentTimeMillis() / 1000;
        List<Message> messages = messageService.findByCreatedAt(ZonedDateTime.now().minusDays(chatServerProperties.getMessageHistoryDays()));
        if(!messages.isEmpty()) {
            List<UserGroupMessage> userGroupMessages = userGroupMessageRepository.findByMessageIn(messages);
            if(!userGroupMessages.isEmpty()){
                userGroupMessages.forEach(userGroupMessage -> {
                    log.debug("deleting userGroupMessage: {}", userGroupMessage);
                    userGroupMessageRepository.delete(userGroupMessage);
                    log.info("userGroupMessage deleted successfully");
                });
            }
            messages.forEach(messageService::deleteMessage);
        }
        long endTime = System.currentTimeMillis() / 1000;
        log.debug("Cron job execution ended. Total time taken: {} ", endTime - startTime);
    }

    public void deleteUserGroupMessage(Long userGroupMessageId) {
        UserGroupMessage userGroupMessage = getUserGroupMessage(userGroupMessageId);
        userGroupMessageRepository.delete(userGroupMessage);
    }

//    @Transactional
//    public void inactiveUserGroupMessage(Long userGroupMessageId) {
//        UserGroupMessage userGroupMessage = getUserGroupMessage(userGroupMessageId);
//        userGroupMessage.setActive(Boolean.FALSE);
//        userGroupMessageRepository.save(userGroupMessage);
//        userGroupService.getUserGroup(userGroupMessage.get)
//    }
}
