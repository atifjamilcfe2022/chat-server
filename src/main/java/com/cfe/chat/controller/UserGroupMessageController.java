package com.cfe.chat.controller;

import com.cfe.chat.controller.dto.GroupMessageHistoryDto;
import com.cfe.chat.controller.mapper.UserMapper;
import com.cfe.chat.domain.custom.GroupMessageHistory;
import com.cfe.chat.controller.response.GroupMessageHistoryResponse;
import com.cfe.chat.enums.MessageStatus;
import com.cfe.chat.repository.UserGroupMessageRepository;
import com.cfe.chat.service.UserGroupMessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//@Slf4j
//@RestController
//@AllArgsConstructor
//@RequestMapping("/userGroupMessages")
//@CrossOrigin(origins = "*")
public class UserGroupMessageController {

//    private final UserGroupMessageService userGroupMessageService;
//    private final UserMapper userMapper;

//    @GetMapping("/groups/{groupId}/history")
//    private ResponseEntity<?> messagesHistoryByGroup(@PathVariable Long groupId) {
//        log.debug("Getting messages history of a group");
//        List<GroupMessageHistory> groupMessageHistories = userGroupMessageService.messagesHistoryByGroup(groupId);
//        log.info("group messages of user found :{}", groupMessageHistories.size());
//
//        List<GroupMessageHistoryDto> groupMessageHistoryDtos = new ArrayList<>();
//        groupMessageHistories.forEach(groupMessageHistory -> {
//            GroupMessageHistoryDto groupMessageHistoryDto =
//                    GroupMessageHistoryDto.builder()
//                            .id(groupMessageHistory.getId())
//                            .messageBody(groupMessageHistory.getMessageBody())
//                            .sender(userMapper.toUserDto(groupMessageHistory.getSender()))
//                            .createdAt(groupMessageHistory.getCreatedAt())
//                            .build();
//            groupMessageHistoryDtos.add(groupMessageHistoryDto);
//        });
//        return ResponseEntity.ok(GroupMessageHistoryResponse.builder().count(groupMessageHistories.size()).data(groupMessageHistoryDtos).build());
//    }

//    @PutMapping("/{userGroupMessageId}/status/{messageStatus}")
//    private ResponseEntity<?> updateStatusOfUserGroupMessage(@PathVariable Long userGroupMessageId,
//                                                                        @PathVariable MessageStatus messageStatus) {
//        log.debug("Updating messageStatus of a userGroup message");
//        userGroupMessageService.updateStatusOfUserGroupMessage(userGroupMessageId, messageStatus);
//        log.info("Updating messageStatus of a userGroup message");
//        return ResponseEntity.noContent().build();
//    }

//    @DeleteMapping("/{userGroupMessageId}")
//    private ResponseEntity<?> deleteUserGroupMessage(@PathVariable Long userGroupMessageId) {
//        log.debug("deleting userGroupMessage and all parent");
//        userGroupMessageService.deleteUserGroupMessage(userGroupMessageId);
//        log.info("deleted userGroupMessage");
//        return ResponseEntity.noContent().build();
//    }
//
//    @PutMapping("/{userGroupMessageId}/inactive")
//    private ResponseEntity<?> inactiveUserGroupMessage(@PathVariable Long userGroupMessageId) {
//        log.debug("inactive userGroupMessage and all parent");
//        userGroupMessageService.inactiveUserGroupMessage(userGroupMessageId);
//        log.info("inactive userGroupMessage");
//        return ResponseEntity.ok().build();
//    }
}
