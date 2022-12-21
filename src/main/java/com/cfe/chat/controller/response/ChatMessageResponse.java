package com.cfe.chat.controller.response;

import com.cfe.chat.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageResponse {

    private Long senderId;
    private Long receiverId;
    private String messageContent;
    private MessageType messageType;
    private String date;
    private Long groupId;
    private Long messageId;
}
