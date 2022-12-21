package com.cfe.chat.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatGroupRequest {

    private Long chatGroupId;
    private String name;
    private boolean active;
}
