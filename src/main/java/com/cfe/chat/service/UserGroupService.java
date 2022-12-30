package com.cfe.chat.service;

import com.cfe.chat.controller.request.AddGroupAndUserGroupsRequest;
import com.cfe.chat.controller.request.GroupRequest;
import com.cfe.chat.controller.request.UserGroupRequest;
import com.cfe.chat.domain.Group;
import com.cfe.chat.domain.UserGroup;
import com.cfe.chat.exception.DataNotFoundException;
import com.cfe.chat.repository.UserGroupRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                .user(userService.getUser(userGroupRequest.getUserId()))
                .group(groupService.getGroup(userGroupRequest.getGroupId()))
                .build();
        userGroupRepository.save(userGroup);
        log.info("User group saved: {}", userGroup.getId());
        return userGroup;
    }

    public UserGroup updateUserGroup(UserGroupRequest userGroupRequest) {
        log.debug("updating user group details: {}", userGroupRequest);
        UserGroup userGroup = getUserGroup(userGroupRequest.getUserGroupId());

        userGroup.setUser(userService.getUser(userGroupRequest.getUserId()));
        userGroup.setGroup(groupService.getGroup(userGroupRequest.getGroupId()));

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
        List<UserGroup> userGroups = userGroupRepository.findUserGroup(groupService.getGroup(groupId));
        log.info("{} users found by group: {}", userGroups.size(), groupId);
        return userGroups;
    }

    public List<UserGroup> findGroupsOfUser(Long userId) {
        log.debug("finding groups of user by user id: {}", userId);
        List<UserGroup> userGroups = userGroupRepository.findUserGroup(userService.getUser(userId));
        log.info("{} groups found by user: {}", userGroups.size(), userId);
        return userGroups;
    }

//    public UserGroup findByUserGroup(Long userId, Long groupId) {
//        log.debug("finding user groups by user id: {} and group id: {}", userId, groupId);
//        UserGroup userGroup = userGroupRepository.findByUserGroup(userService.getUser(userId), groupService.getChatGroup(groupId))
//                .orElseThrow(() -> new DataNotFoundException("User group not found with userId: "
//                        + userId + ", and groupId: " + groupId));
//        log.info("user group found: {}", userGroup);
//        return userGroup;
//    }

    @Transactional
    public Group addGroupAndUserGroups(Long groupId, AddGroupAndUserGroupsRequest addGroupAndUserGroupsRequest) {
        log.debug("add group and userGroups request: {}", addGroupAndUserGroupsRequest);
        Group group;
        if (groupId == null || groupId == 0) {
            group = groupService.addGroup(
                    GroupRequest.builder()
                            .name(addGroupAndUserGroupsRequest.getName())
                            .active(Boolean.TRUE)
                            .build()
            );
        } else {
            group = groupService.getGroup(groupId);
        }
        if (!CollectionUtils.isEmpty(addGroupAndUserGroupsRequest.getUserIds())) {
            List<Long> userIds = new ArrayList<>();
            findUsersInGroup(group.getId()).forEach(userGroup -> {
                userIds.add(userGroup.getUser().getId());
                if(addGroupAndUserGroupsRequest.getUserIds().contains(userGroup.getUser().getId())){
                    userGroup.setActive(Boolean.TRUE);
                    userGroupRepository.save(userGroup);
                }
            });
            addGroupAndUserGroupsRequest.getUserIds().forEach(userId -> {
                if (!userIds.contains(userId)) {
                    UserGroup userGroup = UserGroup.builder()
                            .user(userService.getUser(userId))
                            .group(group)
                            .active(Boolean.TRUE)
                            .build();
                    userGroupRepository.save(userGroup);
                } else {
                    log.warn("User with id: {} is already present in the group: {}", userId, groupId);
                }
            });
        }
        log.info("group and userGroup information saved successfully");
        return group;
    }

    public void inactiveUserFromGroup(Long groupId, Long userId) {
        log.debug("marking user: {} in group: {} inactive", userId, groupId);
        Optional<UserGroup> userGroupOptional = userGroupRepository.findByUserGroup(
                userService.getUser(userId), groupService.getGroup(groupId));
        if (userGroupOptional.isEmpty()) {
            throw new DataNotFoundException("User: " + userId + " is not found in group: " + groupId);
        }
        UserGroup userGroup = userGroupOptional.get();
        userGroup.setActive(Boolean.FALSE);
        userGroupRepository.save(userGroup);
        log.info("user: {} is successfully marked inactive in Group: {}", userId, groupId);
    }

    public void inactiveUserGroup(Long userGroupId) {
        log.debug("marking userGroup: {} inactive", userGroupId);
        UserGroup userGroup = getUserGroup(userGroupId);
        userGroup.setActive(Boolean.FALSE);
        userGroupRepository.save(userGroup);
        log.info("userGroup {} is successfully marked inactive", userGroupId);
    }
}
