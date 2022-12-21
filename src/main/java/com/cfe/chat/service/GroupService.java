package com.cfe.chat.service;

import com.cfe.chat.controller.request.ChatGroupRequest;
import com.cfe.chat.domain.Group;
import com.cfe.chat.exception.DataNotFoundException;
import com.cfe.chat.repository.GroupRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    public Group getChatGroup(Long chatGroupId) {
        log.debug("Getting Chat Group by Id: {}", chatGroupId);

        Group group = groupRepository.findById(chatGroupId).orElseThrow(
                () -> new DataNotFoundException("Chat group not found with id: " + chatGroupId));

        log.info("Chat Group: {}", group);
        return group;
    }

    public List<Group> getChatGroups() {
        log.debug("Getting Chat Groups");

        List<Group> groups = groupRepository.findAll();

        log.info("Chat groups found: {}", groups.size());
        return groups;
    }

    public Group addChatGroup(ChatGroupRequest chatGroupRequest) {
        log.debug("Saving Chat Group: {}", chatGroupRequest);

        Group group = Group.builder()
                .name(chatGroupRequest.getName())
                .active(Boolean.TRUE)
                .build();

        groupRepository.save(group);

        log.info("Chat Group saved: {}", group);
        return group;
    }

    public void updateChatGroup(ChatGroupRequest chatGroupRequest) {
        log.debug("Updating Chat Group: {}", chatGroupRequest);

        Group group = getChatGroup(chatGroupRequest.getChatGroupId());

        if (chatGroupRequest.getName() != null || !chatGroupRequest.getName().isEmpty()) {
            group.setName(chatGroupRequest.getName());
        }
        groupRepository.save(group);
        log.info("Chat Group updated: {}", group);
    }

    public void deleteChatGroup(Long chatGroupId) {
        log.debug("Deleting Chat Group: {}", chatGroupId);

        Group group = getChatGroup(chatGroupId);

        groupRepository.delete(group);
        log.info("Chat Group deleted: {}", group);

    }
}
