package com.cfe.chat.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupRequest {

    private Long userGroupId;
    private String name;
    private Long userId;
    private Long groupId;
    private boolean active;
}
