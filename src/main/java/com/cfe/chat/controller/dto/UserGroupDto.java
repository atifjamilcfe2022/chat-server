package com.cfe.chat.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupDto {

    private UserDto user;
    private GroupDto group;
    private boolean active;
    private OffsetDateTime createdAt;
}
