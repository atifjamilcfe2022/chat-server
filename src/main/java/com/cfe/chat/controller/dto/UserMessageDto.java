package com.cfe.chat.controller.dto;

import com.cfe.chat.enums.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserMessageDto {

    private Long id;
    private MessageStatus messageStatus;
    private Boolean active;
    private OffsetDateTime createdAt;
    private UserDto receiver;
    private MessageDto message;
}
