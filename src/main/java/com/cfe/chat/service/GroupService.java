package com.cfe.chat.service;

import com.cfe.chat.config.properties.ChatServerProperties;
import com.cfe.chat.controller.dto.GroupDto;
import com.cfe.chat.controller.dto.GroupMessageHistoryDto;
import com.cfe.chat.controller.dto.UserDto;
import com.cfe.chat.controller.dto.UserGroupDto;
import com.cfe.chat.controller.mapper.GroupMapper;
import com.cfe.chat.controller.mapper.UserGroupMapper;
import com.cfe.chat.controller.mapper.UserMapper;
import com.cfe.chat.controller.request.AddGroupRequest;
import com.cfe.chat.controller.request.UpdateGroupRequest;
import com.cfe.chat.controller.response.GroupMessageHistoryResponse;
import com.cfe.chat.controller.response.GroupResponse;
import com.cfe.chat.controller.response.UserGroupResponse;
import com.cfe.chat.domain.*;
import com.cfe.chat.domain.custom.GroupMessageHistory;
import com.cfe.chat.enums.MessageStatus;
import com.cfe.chat.exception.DataNotFoundException;
import com.cfe.chat.repository.GroupRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class GroupService {

    private final ChatServerProperties chatServerProperties;

    private final GroupRepository groupRepository;
    private final UserGroupService userGroupService;

    private final UserGroupMessageService userGroupMessageService;

    private final MessageService messageService;

    private final GroupMapper groupMapper;
    private final UserGroupMapper userGroupMapper;
    private final UserMapper userMapper;

    public Group getGroup(Long groupId) {
        log.debug("Getting group by Id: {}", groupId);
        Group group = groupRepository.findById(groupId).orElseThrow(
                () -> new DataNotFoundException("Group not found with id: " + groupId));
        log.info("Group: {}", group);
        return group;
    }

    public GroupDto getGroupDetails(Long groupId) {
        Group group = getGroup(groupId);

        List<User> users = userGroupService.findUsersInGroup(group);
        List<UserDto> userList = new ArrayList<>();
        if (!users.isEmpty()) {
            users.forEach(user -> {
                UserDto userDto = userMapper.toUserDto(user);
                userList.add(userDto);
            });
        }
        GroupDto groupDto = groupMapper.toGroupDto(group);
        groupDto.setUsers(userList);
        return groupDto;
    }

    public GroupResponse getGroups() {
        log.debug("Getting groups");
        List<Group> groups = groupRepository.findAll();
        log.info("{} groups found", groups.size());
        List<GroupDto> groupList = new ArrayList<>();
        if (!groups.isEmpty()) {
            groups.forEach(group -> {
                List<User> users = userGroupService.findUsersInGroup(group);
                List<UserDto> userList = new ArrayList<>();
                if (!users.isEmpty()) {
                    users.forEach(user -> {
                        UserDto userDto = userMapper.toUserDto(user);
                        userList.add(userDto);
                    });
                }
                GroupDto groupDto = groupMapper.toGroupDto(group);
                groupDto.setUsers(userList);
                groupList.add(groupDto);
            });
        }
        return GroupResponse.builder().count(groups.size()).groups(groupList).build();
    }

    @Transactional
    public GroupDto addGroup(AddGroupRequest addGroupRequest) {
        log.debug("Saving group: {}", addGroupRequest);

        Group group = Group.builder()
                .name(addGroupRequest.getName())
                .active(Boolean.TRUE)
                .build();
        groupRepository.save(group);
        log.info("Group created successfully: {}", group);

        if (group.getId() != null && group.getId() > 0) {
            log.info("saving group users");
            userGroupService.addUsersInGroup(group, addGroupRequest.getUserIds());
        }
        return groupMapper.toGroupDto(group);
    }

    @Transactional
    public void updateGroup(UpdateGroupRequest updateGroupRequest) {
        log.debug("Updating Chat Group: {}", updateGroupRequest);

        Group group = getGroup(updateGroupRequest.getGroupId());

        if (updateGroupRequest.getName() != null || !updateGroupRequest.getName().isEmpty()) {
            group.setName(updateGroupRequest.getName());
        }
        groupRepository.save(group);
        log.info("Group updated: {}", group);

        userGroupService.updateUsersInGroup(group, updateGroupRequest.getUserIds());
    }

    @Transactional
    public void deletingGroup(Long groupId) {
        log.debug("Deleting Group: {}", groupId);

        Group group = getGroup(groupId);

        userGroupService.deleteUsersByGroup(group);
        groupRepository.delete(group);
        log.info("Group deleted: {}", group);
    }

    @Transactional
    public void deletingUserFromGroup(Long groupId, Long userId) {
        log.debug("Deleting Group: {}", groupId);
        Group group = getGroup(groupId);
        boolean isGroupNeedsToDelete = userGroupService.deleteUsersFromGroup(group, userId);
        if (isGroupNeedsToDelete) {
            groupRepository.delete(group);
        }
    }

    public GroupResponse getGroupDetailsByUser(Long userId) {
        OffsetDateTime dateTime = OffsetDateTime.now().minusDays(chatServerProperties.getMessageHistoryDays());
        List<Group> groups = userGroupService.findGroupsOfUser(userId);
        List<GroupDto> groupList = new ArrayList<>();
        if (!groups.isEmpty()) {
            groups.forEach(group -> {
                GroupDto groupDto = groupMapper.toGroupDto(group);
                List<User> users = userGroupService.findUsersInGroup(group);
                List<UserDto> userDtoList = new ArrayList<>();
                if (!users.isEmpty()) {
                    users.forEach(user -> userDtoList.add(userMapper.toUserDto(user)));
                }
                groupDto.setUsers(userDtoList);
                groupDto.setGroupMessageHistory(userGroupService.getLastMessageInGroup(dateTime, group));
                groupDto.setUnreadMessageCount(userGroupService.getUnreadMessageOfUserInGroup(userId, group));
                groupList.add(groupDto);
            });
        }
        return GroupResponse.builder().count(groups.size()).groups(groupList).build();
    }

    public GroupMessageHistoryResponse messagesHistoryByGroup(Long groupId) {
        log.debug("finding Messages history by group: {}", groupId);
        OffsetDateTime dateTime = OffsetDateTime.now().minusDays(chatServerProperties.getMessageHistoryDays());
        List<GroupMessageHistory> groupMessageHistories = userGroupService.messagesHistoryByGroup(getGroup(groupId), dateTime);
        log.info("found: {} UserGroupMessages", groupMessageHistories.size());

        List<GroupMessageHistoryDto> groupMessageHistoryDtos = new ArrayList<>();
        groupMessageHistories.forEach(groupMessageHistory -> {
            GroupMessageHistoryDto groupMessageHistoryDto =
                    GroupMessageHistoryDto.builder()
                            .id(groupMessageHistory.getId())
                            .messageBody(groupMessageHistory.getMessageBody())
                            .sender(userMapper.toUserDto(groupMessageHistory.getSender()))
                            .createdAt(groupMessageHistory.getCreatedAt())
                            .build();
            groupMessageHistoryDtos.add(groupMessageHistoryDto);
        });
        return GroupMessageHistoryResponse.builder().count(groupMessageHistories.size()).data(groupMessageHistoryDtos).build();
    }

    @Scheduled(cron = "${cron.expression.group}")
    public void deleteOldGroupMessage() {
        log.debug("Cron job execution started for deleting group messages");
        long startTime = System.currentTimeMillis() / 1000;
        List<Message> messages = messageService.findByCreatedAt(ZonedDateTime.now().minusDays(chatServerProperties.getMessageHistoryDays()));
        if (!messages.isEmpty()) {
            List<UserGroupMessage> userGroupMessages = userGroupMessageService.findByMessageIn(messages);
            if (!userGroupMessages.isEmpty()) {
                userGroupMessages.forEach(userGroupMessage -> {
                    log.debug("deleting userGroupMessage: {}", userGroupMessage);
                    userGroupMessageService.delete(userGroupMessage);
                    log.info("userGroupMessage deleted successfully");
                });
            }
            messages.forEach(messageService::deleteMessage);
        }
        long endTime = System.currentTimeMillis() / 1000;
        log.debug("Cron job execution ended. Total time taken: {} ", endTime - startTime);
    }

    public GroupResponse updateLastReadGroupMessageForUser(Long groupId, Long userId, Long messageId) {
        userGroupService.updateLastReadGroupMessageForUser(getGroup(groupId), userId, messageService.getMessage(messageId));
        return getGroupDetailsByUser(userId);
    }

//    public void updateUserGroupMessageStatus(Long userGroupMessageId, MessageStatus messageStatus) {
//        userGroupMessageService.updateStatusOfUserGroupMessage(userGroupMessageId, messageStatus);
//    }
//
//    @Transactional
//    public void markAllRead(Long groupId, Long userId) {
//        userGroupService.markAllRead(getGroup(groupId), userId);
//    }
}
