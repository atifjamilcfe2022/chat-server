package com.cfe.chat.controller.dto;

import com.cfe.chat.enums.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserMessageDto {

    private Long userMessageId;
    private MessageStatus messageStatus;
    private Boolean active;
    private UserDto user;
    private MessageDto message;
}
