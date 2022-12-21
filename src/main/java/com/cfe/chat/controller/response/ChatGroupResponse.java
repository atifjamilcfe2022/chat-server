package com.cfe.chat.controller.response;

import com.cfe.chat.controller.dto.GroupDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatGroupResponse {

    private int count;
    private List<GroupDto> groupDtos;
}
