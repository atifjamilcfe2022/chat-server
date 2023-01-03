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
public class GroupResponse {

    private int count;
    private List<GroupDto> groups;
}
