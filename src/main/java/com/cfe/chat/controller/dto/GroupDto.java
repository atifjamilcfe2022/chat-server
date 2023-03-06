package com.cfe.chat.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupDto {

    private Long id;
    private String name;
    private boolean active;
//    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ")
    private OffsetDateTime createdAt;
    private List<UserDto> users;
    private GroupMessageHistoryDto groupMessageHistory;
    private Integer unreadMessageCount;
}
