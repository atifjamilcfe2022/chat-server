package com.cfe.chat.controller.request;

import com.cfe.chat.enums.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest {

    private Long messageId;
    private String messageBody;
    private MessageStatus messageStatus;
    private Long userId;
    private boolean active;
}
