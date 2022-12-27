package com.cfe.chat.controller;

import com.cfe.chat.controller.dto.GroupDto;
import com.cfe.chat.controller.mapper.GroupMapper;
import com.cfe.chat.controller.request.GroupRequest;
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
@CrossOrigin(origins = "*")
public class GroupController {

    private final GroupService groupService;
    private final GroupMapper groupMapper;

    @GetMapping
    private ResponseEntity<?> groups() {
        log.debug("getting groups");
        List<Group> groups = groupService.getGroups();
        List<GroupDto> groupDtos = new ArrayList<>();
        groups.forEach(chatGroup -> groupDtos.add(groupMapper.toGroupDto(chatGroup)));
        log.info("chat groups found :{}", groupDtos.size());
        return ResponseEntity.ok(ChatGroupResponse.builder().count(groupDtos.size()).groupDtos(groupDtos).build());
    }

    @Operation(summary = "Get a group by its id")
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
        Group group = groupService.getGroup(groupId);
        return ResponseEntity.ok(groupMapper.toGroupDto(group));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private ResponseEntity<?> chatGroup(@RequestBody GroupRequest groupRequest) {
        log.debug("adding ChatGroup: {}", groupRequest);
        Group group = groupService.addGroup(groupRequest);
        return ResponseEntity.ok(groupMapper.toGroupDto(group));
    }

    @PutMapping("/{groupId}")
    @ResponseStatus(HttpStatus.OK)
    private ResponseEntity<?> updateChatGroup(@Parameter(description = "id of group to be updated") @PathVariable Long groupId,
                                              @RequestBody GroupRequest groupRequest) {
        log.debug("updating Group: {}", groupRequest);
        if(!groupId.equals(groupRequest.getGroupId())) {
            throw new InvalidDataUpdateException("Object in request in not matched with required in path");
        }
        groupService.updateGroup(groupRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private ResponseEntity<?> updateGroup(@Parameter(description = "id of group to be deleted") @PathVariable Long groupId) {
        log.debug("Deleting group by id: {}", groupId);

        groupService.deleteGroup(groupId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
