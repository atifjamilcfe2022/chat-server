package com.cfe.chat.controller.response;

import com.cfe.chat.domain.User;
import com.cfe.chat.enums.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupMessageHistory {

    private long id;
    private String messageBody;
    private User sender;
//    private User receiver;
//    private MessageStatus messageStatus;
//    private LocalDateTime createdAt;
}
