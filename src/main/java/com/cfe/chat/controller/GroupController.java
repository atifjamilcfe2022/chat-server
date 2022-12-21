package com.cfe.chat.controller;

import com.cfe.chat.controller.dto.GroupDto;
import com.cfe.chat.controller.mapper.GroupMapper;
import com.cfe.chat.controller.request.ChatGroupRequest;
import com.cfe.chat.controller.response.ChatGroupResponse;
import com.cfe.chat.domain.Group;
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
public class GroupController {

    private final GroupService groupService;
    private final GroupMapper groupMapper;

    @GetMapping
    private ResponseEntity<?> chatGroups() {
        log.debug("getting ChatGroups");
        List<Group> groups = groupService.getChatGroups();
        List<GroupDto> groupDtos = new ArrayList<>();
        groups.forEach(chatGroup -> groupDtos.add(groupMapper.toGroupDto(chatGroup)));
        log.info("chat groups found :{}", groupDtos.size());
        return ResponseEntity.ok(ChatGroupResponse.builder().count(groupDtos.size()).groupDtos(groupDtos).build());
    }

    @Operation(summary = "Get a chat group by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the chat group",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GroupDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Chat group not found",
                    content = @Content)})
    @GetMapping("/{chatGroupId}")
    private ResponseEntity<?> chatGroup(@Parameter(description = "id of group to be searched")
                                            @PathVariable Long chatGroupId) {
        log.debug("getting ChatGroup by Id: {}", chatGroupId);
        Group group = groupService.getChatGroup(chatGroupId);
        return ResponseEntity.ok(groupMapper.toGroupDto(group));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private ResponseEntity<?> addChatGroup(@RequestBody ChatGroupRequest chatGroupRequest) {
        log.debug("adding ChatGroup: {}", chatGroupRequest);
        Group group = groupService.addChatGroup(chatGroupRequest);
        return ResponseEntity.ok(groupMapper.toGroupDto(group));
    }

    @PutMapping("/{chatGroupId}")
    @ResponseStatus(HttpStatus.OK)
    private ResponseEntity<?> updateChatGroup(@PathVariable Long chatGroupId,
                                              @RequestBody ChatGroupRequest chatGroupRequest) {
        log.debug("updating ChatGroup: {}", chatGroupRequest);
        if(!chatGroupId.equals(chatGroupRequest.getChatGroupId())) {
            throw new InvalidDataUpdateException("Object in request in not matched with required in path");
        }
        groupService.updateChatGroup(chatGroupRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{chatGroupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private ResponseEntity<?> updateChatGroup(@PathVariable Long chatGroupId) {
        log.debug("Deleting chat group by id: {}", chatGroupId);

        groupService.deleteChatGroup(chatGroupId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
