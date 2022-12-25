package com.cfe.chat.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupMessageHistoryDto {

    private long id;
    private String messageBody;
    private UserDto sender;
}
