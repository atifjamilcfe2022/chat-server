package com.cfe.chat.controller;

import com.cfe.chat.controller.dto.MessageDto;
import com.cfe.chat.controller.mapper.MessageMapper;
import com.cfe.chat.controller.request.MessageRequest;
import com.cfe.chat.controller.response.MessageResponse;
import com.cfe.chat.domain.Message;
import com.cfe.chat.exception.InvalidDataUpdateException;
import com.cfe.chat.service.MessageService;
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
@RequestMapping("/messages")
@CrossOrigin(origins = "*")
public class MessageController {

    private final MessageService messageService;
    private final MessageMapper messageMapper;

    @GetMapping
    private ResponseEntity<?> messages() {
        log.debug("getting messages");
        List<Message> messages = messageService.getMessages();
        List<MessageDto> messageDtos = new ArrayList<>();
        messages.forEach(message -> messageDtos.add(messageMapper.toMessageDto(message)));
        log.info("chat messages found :{}", messageDtos.size());
        return ResponseEntity.ok(MessageResponse.builder().count(messageDtos.size()).messageDtos(messageDtos).build());
    }

    @GetMapping("/{messageId}")
    private ResponseEntity<?> message(@PathVariable Long messageId) {
        log.debug("getting Message by Id: {}", messageId);
        Message message = messageService.getMessage(messageId);
        return ResponseEntity.ok(messageMapper.toMessageDto(message));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private ResponseEntity<?> addMessage(@RequestBody MessageRequest messageRequest) {
        log.debug("adding Message: {}", messageRequest);
        Message message = messageService.addMessage(messageRequest);
        return ResponseEntity.ok(messageMapper.toMessageDto(message));
    }

    @PutMapping("/{messageId}")
    @ResponseStatus(HttpStatus.OK)
    private ResponseEntity<?> updateMessage(@PathVariable Long messageId,
                                              @RequestBody MessageRequest messageRequest) {
        log.debug("updating Message: {}", messageRequest);
        if(!messageId.equals(messageRequest.getMessageId())) {
            throw new InvalidDataUpdateException("Object in request in not matched with required in path");
        }
        messageService.updateMessage(messageRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{messageId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private ResponseEntity<?> updateMessage(@PathVariable Long messageId) {
        log.debug("Deleting chat message by id: {}", messageId);

        messageService.deleteMessage(messageId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
