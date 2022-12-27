package com.cfe.chat.service;

import com.cfe.chat.controller.request.GroupRequest;
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

    public Group getGroup(Long groupId) {
        log.debug("Getting group by Id: {}", groupId);

        Group group = groupRepository.findById(groupId).orElseThrow(
                () -> new DataNotFoundException("Group not found with id: " + groupId));

        log.info("Group: {}", group);
        return group;
    }

    public List<Group> getGroups() {
        log.debug("Getting groups");

        List<Group> groups = groupRepository.findAll();

        log.info("Groups found: {}", groups.size());
        return groups;
    }

    public Group addGroup(GroupRequest groupRequest) {
        log.debug("Saving group: {}", groupRequest);

        Group group = Group.builder()
                .name(groupRequest.getName())
                .active(Boolean.TRUE)
                .build();

        groupRepository.save(group);

        log.info("Group saved: {}", group);
        return group;
    }

    public void updateGroup(GroupRequest groupRequest) {
        log.debug("Updating Chat Group: {}", groupRequest);

        Group group = getGroup(groupRequest.getGroupId());

        if (groupRequest.getName() != null || !groupRequest.getName().isEmpty()) {
            group.setName(groupRequest.getName());
        }
        groupRepository.save(group);
        log.info("Group updated: {}", group);
    }

    public void deleteGroup(Long chatGroupId) {
        log.debug("Deleting Chat Group: {}", chatGroupId);

        Group group = getGroup(chatGroupId);

        groupRepository.delete(group);
        log.info("Group deleted: {}", group);
    }
}
