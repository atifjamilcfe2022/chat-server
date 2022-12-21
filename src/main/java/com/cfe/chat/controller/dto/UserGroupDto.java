package com.cfe.chat.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupDto {

    private UserDto user;
    private GroupDto group;
    private boolean active;
}
