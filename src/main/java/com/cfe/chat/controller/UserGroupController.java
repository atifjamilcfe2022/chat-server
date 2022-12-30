package com.cfe.chat.controller;

import com.cfe.chat.controller.dto.UserGroupDto;
import com.cfe.chat.controller.mapper.UserGroupMapper;
import com.cfe.chat.controller.request.AddGroupAndUserGroupsRequest;
import com.cfe.chat.controller.request.UserGroupRequest;
import com.cfe.chat.controller.response.AddGroupAndUserGroupsResponse;
import com.cfe.chat.controller.response.UserGroupResponse;
import com.cfe.chat.domain.Group;
import com.cfe.chat.domain.UserGroup;
import com.cfe.chat.exception.InvalidDataUpdateException;
import com.cfe.chat.service.UserGroupService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/userGroups")
@CrossOrigin(origins = "*")
public class UserGroupController {

    private final UserGroupService userGroupService;
    private final UserGroupMapper userGroupMapper;

    @GetMapping
    private ResponseEntity<?> userGroups() {
        log.debug("getting UserGroups");
        List<UserGroup> userGroups = userGroupService.findUserGroups();
        List<UserGroupDto> userGroupDtos = new ArrayList<>();
        userGroups.forEach(userGroup -> userGroupDtos.add(userGroupMapper.toUserGroupDto(userGroup)));
        log.info("user groups found :{}", userGroupDtos.size());
        return ResponseEntity.ok(UserGroupResponse.builder().count(userGroupDtos.size()).userGroupDtos(userGroupDtos).build());
    }

    @GetMapping("/{userGroupId}")
    private ResponseEntity<?> userGroup(@PathVariable Long userGroupId) {
        log.debug("getting UserGroup by Id: {}", userGroupId);
        UserGroup userGroup = userGroupService.getUserGroup(userGroupId);
        return ResponseEntity.ok(userGroupMapper.toUserGroupDto(userGroup));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private ResponseEntity<?> addUserGroup(@RequestBody UserGroupRequest userGroupRequest) {
        log.debug("adding UserGroup: {}", userGroupRequest);
        UserGroup userGroup = userGroupService.addUserGroup(userGroupRequest);
        return ResponseEntity.ok(userGroupMapper.toUserGroupDto(userGroup));
    }

    @PutMapping("/{userGroupId}")
    @ResponseStatus(HttpStatus.OK)
    private ResponseEntity<?> updateUserGroup(@PathVariable Long userGroupId,
                                              @RequestBody UserGroupRequest userGroupRequest) {
        log.debug("updating UserGroup: {}", userGroupRequest);
        if (!userGroupId.equals(userGroupRequest.getUserGroupId())) {
            throw new InvalidDataUpdateException("Object in request in not matched with required in path");
        }
        userGroupService.updateUserGroup(userGroupRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userGroupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private ResponseEntity<?> updateUserGroup(@PathVariable Long userGroupId) {
        log.debug("Deleting user userGroup by id: {}", userGroupId);

        userGroupService.deleteUserGroup(userGroupId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/groups/{groupId}/users")
    private ResponseEntity<?> usersInGroup(@PathVariable Long groupId) {
        log.debug("Getting users in group");
        List<UserGroup> userGroups = userGroupService.findUsersInGroup(groupId);
        List<UserGroupDto> userGroupDtos = new ArrayList<>();
        userGroups.forEach(userGroup -> userGroupDtos.add(userGroupMapper.toUserGroupDto(userGroup)));
        log.info("users in group found :{}", userGroupDtos.size());
        return ResponseEntity.ok(UserGroupResponse.builder().count(userGroupDtos.size()).userGroupDtos(userGroupDtos).build());
    }

    @GetMapping("/users/{userId}/groups")
    private ResponseEntity<?> groupsOfUser(@PathVariable Long userId) {
        log.debug("Getting users in group");
        List<UserGroup> userGroups = userGroupService.findGroupsOfUser(userId);
        List<UserGroupDto> userGroupDtos = new ArrayList<>();
        userGroups.forEach(userGroup -> userGroupDtos.add(userGroupMapper.toUserGroupDto(userGroup)));
        log.info("groups of user found :{}", userGroupDtos.size());
        return ResponseEntity.ok(UserGroupResponse.builder().count(userGroupDtos.size()).userGroupDtos(userGroupDtos).build());
    }

    @PostMapping("/groups/{groupId}/users")
    @ResponseStatus(HttpStatus.CREATED)
    private ResponseEntity<?> addGroupAndUserGroups(
            @Positive @PathVariable Long groupId,
            @RequestBody AddGroupAndUserGroupsRequest addGroupAndUserGroupsRequest) {
        log.debug("adding group and UserGroups request: {}", addGroupAndUserGroupsRequest);
        Group group = userGroupService.addGroupAndUserGroups(groupId, addGroupAndUserGroupsRequest);
        return ResponseEntity.ok(AddGroupAndUserGroupsResponse.builder().groupId(group.getId()).build());
    }

    @PutMapping("/exit/groups/{groupId}/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    private ResponseEntity<?> inactiveUserFromGroup(@PathVariable Long groupId, @PathVariable Long userId) {
        log.debug("Deleting user: {} from group: {}", userId, groupId);

        userGroupService.inactiveUserFromGroup(groupId, userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{userGroupId}/exit")
    @ResponseStatus(HttpStatus.OK)
    private ResponseEntity<?> inactiveUserGroup(@PathVariable Long userGroupId) {
        log.debug("Inactivating userGroup: {}", userGroupId);

        userGroupService.inactiveUserGroup(userGroupId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
