package com.cfe.chat.domain.custom;

import com.cfe.chat.domain.User;
import com.cfe.chat.enums.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMessageHistory {

    private long id;
    private String messageBody;
    private User sender;
    private User receiver;
    private MessageStatus messageStatus;
    private OffsetDateTime createdAt;
}
