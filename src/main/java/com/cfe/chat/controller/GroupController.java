package com.cfe.chat.controller;

import com.cfe.chat.controller.dto.GroupDto;
import com.cfe.chat.controller.dto.GroupMessageHistoryDto;
import com.cfe.chat.controller.mapper.GroupMapper;
import com.cfe.chat.controller.request.AddGroupRequest;
import com.cfe.chat.controller.request.UpdateGroupRequest;
import com.cfe.chat.controller.response.GroupMessageHistoryResponse;
import com.cfe.chat.controller.response.GroupResponse;
import com.cfe.chat.domain.Group;
import com.cfe.chat.domain.custom.GroupMessageHistory;
import com.cfe.chat.exception.InvalidDataUpdateException;
import com.cfe.chat.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/groups")
@CrossOrigin(origins = "*")
public class GroupController {

    private final GroupService groupService;
//    private final GroupMapper groupMapper;

    @Operation(summary = "Get all groups")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found groups",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GroupResponse.class))})})
    @GetMapping
    private ResponseEntity<?> groups() {
        log.debug("request received for getting groups");
        GroupResponse groupResponse = groupService.getGroups();
        return ResponseEntity.ok(groupResponse);
    }

    @Operation(summary = "Get a group by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the group",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GroupDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Group not found",
                    content = @Content)})
    @GetMapping("/{groupId}")
    private ResponseEntity<?> group(@Parameter(description = "id of group to be searched") @PathVariable Long groupId) {
        log.debug("getting Group by Id: {}", groupId);
        GroupDto groupDto = groupService.getGroupDetails(groupId);
        return ResponseEntity.ok(groupDto);
    }

    @Operation(summary = "Save new group and users")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private ResponseEntity<?> addGroup(@RequestBody AddGroupRequest addGroupRequest) {
        log.debug("adding ChatGroup: {}", addGroupRequest);
        GroupDto groupDto = groupService.addGroup(addGroupRequest);
        return ResponseEntity.ok(groupDto);
    }

    @Operation(summary = "Update existing group by id")
    @PutMapping("/{groupId}")
    @ResponseStatus(HttpStatus.OK)
    private ResponseEntity<?> updateGroup(@Parameter(description = "id of group to be updated") @PathVariable Long groupId,
                                              @RequestBody UpdateGroupRequest updateGroupRequest) {
        log.debug("updating Group: {}", updateGroupRequest);
        if(!groupId.equals(updateGroupRequest.getGroupId())) {
            throw new InvalidDataUpdateException("Object in request in not matched with required in path");
        }
        groupService.updateGroup(updateGroupRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "delete group by id")
    @DeleteMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private ResponseEntity<?> deleteGroup(@Parameter(description = "id of group to be deleted") @PathVariable Long groupId) {
        log.debug("Deleting group by id: {}", groupId);

        groupService.deletingGroup(groupId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "delete user in group by user id")
    @DeleteMapping("/{groupId}/exit/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private ResponseEntity<?> deleteUserFromGroup(@Parameter(description = "id of group") @PathVariable Long groupId,
                                                  @Parameter(description = "id of user to be deleted") @PathVariable Long userId) {
        log.debug("Deleting user: {} from group by id: {}", userId, groupId);

        groupService.deletingUserFromGroup(groupId, userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Get groups by user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found groups by user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GroupResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Groups not found",
                    content = @Content)})
    @GetMapping("/users/{userId}")
    private ResponseEntity<?> groupByUser(@Parameter(description = "id of user to be searched") @PathVariable Long userId) {
        log.debug("getting Groups by user: {}", userId);
        GroupResponse groupResponse = groupService.getGroupDetailsByUser(userId);
        return ResponseEntity.ok(groupResponse);
    }

    @GetMapping("/{groupId}/history")
    private ResponseEntity<?> messagesHistoryByGroup(@PathVariable Long groupId) {
        log.debug("Getting messages history of a group");
        GroupMessageHistoryResponse groupMessageHistoryResponse = groupService.messagesHistoryByGroup(groupId);
        return ResponseEntity.ok(groupMessageHistoryResponse);
    }
}
