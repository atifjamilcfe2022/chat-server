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
public class UserMessageHistoryDto {

    private long id;
    private String messageBody;
    private UserDto sender;
    private UserDto receiver;
    private MessageStatus messageStatus;
    private OffsetDateTime createdAt;
}
