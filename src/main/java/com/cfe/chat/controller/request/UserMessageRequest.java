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
public class UserMessageRequest {

    private Long userMessageId;
    private MessageStatus messageStatus;
    private Boolean active;
    private Long userId;
    private Long messageId;
}
