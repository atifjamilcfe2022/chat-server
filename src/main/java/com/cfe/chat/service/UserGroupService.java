package com.cfe.chat.service;

import com.cfe.chat.controller.request.UserGroupRequest;
import com.cfe.chat.domain.UserGroup;
import com.cfe.chat.exception.DataNotFoundException;
import com.cfe.chat.repository.UserGroupRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserGroupService {

    private final UserGroupRepository userGroupRepository;
    private final UserService userService;
    private final GroupService groupService;

    public List<UserGroup> findUserGroups() {
        log.debug("getting all user groups");
        List<UserGroup> userGroups = userGroupRepository.findAll();
        log.info("user groups found: {}", userGroups.size());
        return userGroups;
    }

    public UserGroup getUserGroup(Long userGroupId) {
        log.debug("Getting user group by Id: {}", userGroupId);
        UserGroup userGroup = userGroupRepository.findById(userGroupId)
                .orElseThrow(() -> new DataNotFoundException("User group not found with userGroupId" + userGroupId));
        log.info("user group found: {}", userGroup);
        return userGroup;
    }

    public UserGroup addUserGroup(UserGroupRequest userGroupRequest) {
        log.debug("adding user group details: {}", userGroupRequest);
        UserGroup userGroup = UserGroup.builder()
                .name(userGroupRequest.getName())
                .user(userService.getUser(userGroupRequest.getUserId()))
                .group(groupService.getChatGroup(userGroupRequest.getGroupId()))
                .build();
        userGroupRepository.save(userGroup);
        log.info("User group saved: {}", userGroup.getId());
        return userGroup;
    }

    public UserGroup updateUserGroup(UserGroupRequest userGroupRequest) {
        log.debug("updating user group details: {}", userGroupRequest);
        UserGroup userGroup = getUserGroup(userGroupRequest.getUserGroupId());

        userGroup.setName(userGroupRequest.getName());
        userGroup.setUser(userService.getUser(userGroupRequest.getUserId()));
        userGroup.setGroup(groupService.getChatGroup(userGroupRequest.getGroupId()));

        userGroupRepository.save(userGroup);
        log.info("User group updated: {}", userGroup.getId());
        return userGroup;
    }

    public void deleteUserGroup(Long userGroupId) {
        log.debug("deleting user group: {}", userGroupId);
        userGroupRepository.delete(getUserGroup(userGroupId));
        log.info("User group deleted: {}", userGroupId);
    }

    public List<UserGroup> findUsersInGroup(Long groupId) {
        log.debug("finding users in group by group id: {}", groupId);
        List<UserGroup> userGroups = userGroupRepository.findUserGroup(groupService.getChatGroup(groupId));
        log.info("{} users found by group: {}", userGroups.size(), groupId);
        return userGroups;
    }

    public List<UserGroup> findGroupsOfUser(Long userId) {
        log.debug("finding groups of user by user id: {}", userId);
        List<UserGroup> userGroups = userGroupRepository.findUserGroup(userService.getUser(userId));
        log.info("{} groups found by user: {}", userGroups.size(), userId);
        return userGroups;
    }

    public UserGroup findByUserGroup(Long userId, Long groupId) {
        log.debug("finding user groups by user id: {} and group id: {}", userId, groupId);
        UserGroup userGroup = userGroupRepository.findByUserGroup(userService.getUser(userId), groupService.getChatGroup(groupId))
                .orElseThrow(() -> new DataNotFoundException("User group not found with userId: "
                        + userId + ", and groupId: " + groupId));
        log.info("user group found: {}", userGroup);
        return userGroup;
    }
}
