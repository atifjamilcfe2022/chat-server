package com.cfe.chat.controller.request;

import com.cfe.chat.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageRequest {

    private Long senderId;
    private Long receiverId;
    private String messageContent;
    private MessageType messageType;
    private Long groupId;
    private Long userMessageId;
    private Long userGroupMessageId;
}
