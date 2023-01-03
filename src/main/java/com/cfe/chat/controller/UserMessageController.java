package com.cfe.chat.controller;

import com.cfe.chat.controller.dto.UserMessageDto;
import com.cfe.chat.controller.dto.UserMessageHistoryDto;
import com.cfe.chat.controller.mapper.UserMapper;
import com.cfe.chat.controller.mapper.UserMessageMapper;
import com.cfe.chat.controller.request.UserMessageRequest;
import com.cfe.chat.domain.custom.UserMessageHistory;
import com.cfe.chat.controller.response.UserMessageHistoryResponse;
import com.cfe.chat.controller.response.UserMessageResponse;
import com.cfe.chat.domain.UserMessage;
import com.cfe.chat.enums.MessageStatus;
import com.cfe.chat.exception.InvalidDataUpdateException;
import com.cfe.chat.repository.UserRepository;
import com.cfe.chat.service.UserMessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//@Slf4j
//@RestController
//@AllArgsConstructor
//@RequestMapping("/userMessages")
//@CrossOrigin(origins = "*")
public class UserMessageController {

//    private final UserMessageService userMessageService;
//
//    private final UserMessageMapper userMessageMapper;
//    private final UserMapper userMapper;


//    @GetMapping
//    private ResponseEntity<?> userMessages() {
//        log.debug("getting userMessages");
//        List<UserMessage> userMessages = userMessageService.findUserMessages();
//        List<UserMessageDto> userMessageDtos = new ArrayList<>();
//        userMessages.forEach(userMessage -> userMessageDtos.add(userMessageMapper.toUserMessageDto(userMessage)));
//        log.info("user messages found :{}", userMessageDtos.size());
//        return ResponseEntity.ok(UserMessageResponse.builder().count(userMessageDtos.size()).data(userMessageDtos).build());
//    }
//
//    @GetMapping("/{userMessageId}")
//    private ResponseEntity<?> userMessage(@PathVariable Long userMessageId) {
//        log.debug("getting UserMessage by Id: {}", userMessageId);
//        UserMessage userMessage = userMessageService.getUserMessage(userMessageId);
//        return ResponseEntity.ok(userMessageMapper.toUserMessageDto(userMessage));
//    }
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    private ResponseEntity<?> addUserMessage(@RequestBody UserMessageRequest userMessageRequest) {
//        log.debug("adding UserMessage: {}", userMessageRequest);
//        UserMessage userMessage = userMessageService.addUserMessage(userMessageRequest);
//        return ResponseEntity.ok(userMessageMapper.toUserMessageDto(userMessage));
//    }
//
//    @PutMapping("/{userMessageId}")
//    @ResponseStatus(HttpStatus.OK)
//    private ResponseEntity<?> updateUserMessage(@PathVariable Long userMessageId,
//                                              @RequestBody UserMessageRequest userMessageRequest) {
//        log.debug("updating UserMessage: {}", userMessageRequest);
//        if (!userMessageId.equals(userMessageRequest.getUserMessageId())) {
//            throw new InvalidDataUpdateException("Object in request in not matched with required in path");
//        }
//        userMessageService.updateUserMessage(userMessageRequest);
//        return ResponseEntity.ok().build();
//    }
//
//    @DeleteMapping("/{userMessageId}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    private ResponseEntity<?> updateUserMessage(@PathVariable Long userMessageId) {
//        log.debug("Deleting user userMessage by id: {}", userMessageId);
//
//        userMessageService.deleteUserMessage(userMessageId);
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//    }
//
////    @GetMapping("/messages/{messageId}/users")
////    private ResponseEntity<?> usersInMessage(@PathVariable Long messageId) {
////        log.debug("Getting users in message");
////        List<UserMessage> userMessages = userMessageService.findUsersInMessage(messageId);
////        List<UserMessageDto> userMessageDtos = new ArrayList<>();
////        userMessages.forEach(userMessage -> userMessageDtos.add(userMessageMapper.toUserMessageDto(userMessage)));
////        log.info("users in message found :{}", userMessageDtos.size());
////        return ResponseEntity.ok(UserMessageResponse.builder().count(userMessageDtos.size()).userMessageDtos(userMessageDtos).build());
////    }
////
//    @GetMapping("/users/{senderId}/{receiverId}/history")
//    private ResponseEntity<?> messagesHistoryByUser(@PathVariable Long senderId, @PathVariable Long receiverId) {
//        log.debug("Getting users messages history");
//        List<UserMessageHistory> userMessageHistories = userMessageService.userMessagesHistory(senderId, receiverId);
//        log.info("messages of user found :{}", userMessageHistories.size());
//        List<UserMessageHistoryDto> userMessageHistoryDtos = new ArrayList<>();
//        userMessageHistories.forEach(userMessageHistory ->  {
//            UserMessageHistoryDto userMessageHistoryDto = UserMessageHistoryDto.builder()
//                    .id(userMessageHistory.getId())
//                    .messageBody(userMessageHistory.getMessageBody())
//                    .messageStatus(userMessageHistory.getMessageStatus())
//                    .createdAt(userMessageHistory.getCreatedAt())
//                    .sender(userMapper.toUserDto(userMessageHistory.getSender()))
//                    .receiver(userMapper.toUserDto(userMessageHistory.getReceiver()))
//                    .build();
//            userMessageHistoryDtos.add(userMessageHistoryDto);
//                });
//        return ResponseEntity.ok(UserMessageHistoryResponse.builder().count(userMessageHistoryDtos.size()).data(userMessageHistoryDtos).build());
//    }
//
//    @GetMapping("/{userMessageId}/status/{messageStatus}")
//    private ResponseEntity<?> usersInMessage(@PathVariable Long userMessageId,
//                                             @PathVariable MessageStatus messageStatus) {
//        log.debug("Updating userMessage: {}, status to: {}", userMessageId, messageStatus);
//        userMessageService.updateUsersMessageStatus(userMessageId, messageStatus);
//        log.info("Updated users message status");
//        return ResponseEntity.noContent().build();
//    }
//
//    @GetMapping("/{userId}/lastChat")
//    private ResponseEntity<?> lastChat(@PathVariable Long userId) {
//        log.debug("getting UserMessages as lastChat by user Id: {}", userId);
//        List<UserMessage> userMessages = userMessageService.getUserByLastChat(userId);
//        List<UserMessageDto> userMessageDtos = new ArrayList<>();
//        userMessages.forEach(userMessage -> userMessageDtos.add(userMessageMapper.toUserMessageDto(userMessage)));
//        log.info("user messages found :{}", userMessageDtos.size());
//        return ResponseEntity.ok(UserMessageResponse.builder().count(userMessageDtos.size()).data(userMessageDtos).build());
//    }
}
