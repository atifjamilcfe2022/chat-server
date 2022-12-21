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
public class MessageDto {

    private Long id;
    private String messageBody;
    private UserDto user;
    private boolean active;
}
