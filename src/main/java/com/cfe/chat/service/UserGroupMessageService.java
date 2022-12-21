package com.cfe.chat.service;

import com.cfe.chat.config.properties.ChatServerProperties;
import com.cfe.chat.controller.response.GroupMessageHistory;
import com.cfe.chat.domain.*;
import com.cfe.chat.enums.MessageStatus;
import com.cfe.chat.exception.DataNotFoundException;
import com.cfe.chat.repository.UserGroupMessageRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserGroupMessageService {

    private final ChatServerProperties chatServerProperties;
    private final UserGroupMessageRepository userGroupMessageRepository;
    private final UserGroupService userGroupService;

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

    public List<GroupMessageHistory> messagesHistoryByGroupForUser(Long groupId, Long userId) {
        log.debug("finding userGroupMessages history by group: {} for user: {}", groupId, userId);
        LocalDateTime dateTime = LocalDate.now().atStartOfDay().minusDays(chatServerProperties.getMessageHistoryDays());
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
}
