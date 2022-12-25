package com.cfe.chat.domain.custom;

import com.cfe.chat.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupMessageHistory {

    private long id;
    private String messageBody;
    private User sender;
}
