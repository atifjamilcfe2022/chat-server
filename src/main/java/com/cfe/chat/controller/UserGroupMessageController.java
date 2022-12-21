package com.cfe.chat.controller;

import com.cfe.chat.controller.response.GroupMessageHistory;
import com.cfe.chat.controller.response.GroupMessageHistoryResponse;
import com.cfe.chat.controller.response.UserMessageResponse;
import com.cfe.chat.enums.MessageStatus;
import com.cfe.chat.service.UserGroupMessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/userGroupMessages")
public class UserGroupMessageController {

    private final UserGroupMessageService userGroupMessageService;


    @GetMapping("/groups/{groupId}/users/{userId}/history")
    private ResponseEntity<?> messagesHistoryByGroupForUser(@PathVariable Long groupId, @PathVariable Long userId) {
        log.debug("Getting messages history of a group for a user");
        List<GroupMessageHistory> groupMessageHistories = userGroupMessageService.messagesHistoryByGroupForUser(groupId, userId);
        log.info("group messages of user found :{}", groupMessageHistories.size());
        return ResponseEntity.ok(GroupMessageHistoryResponse.builder().count(groupMessageHistories.size()).data(groupMessageHistories).build());
    }

    @GetMapping("/{userGroupMessageId}/status/{messageStatus}")
    private ResponseEntity<?> updateStatusOfUserGroupMessage(@PathVariable Long userGroupMessageId,
                                                                        @PathVariable MessageStatus messageStatus) {
        log.debug("Updating messageStatus of a userGroup message");
        userGroupMessageService.updateStatusOfUserGroupMessage(userGroupMessageId, messageStatus);
        log.info("Updating messageStatus of a userGroup message");
        return ResponseEntity.noContent().build();
    }
}
