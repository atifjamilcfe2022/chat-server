package com.cfe.chat.dto;

import com.cfe.chat.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    private Long groupId;
    private Long senderId;
    private Long receiverId;
    private String messageContent;
    private String date;
    private MessageType messageType;
}
