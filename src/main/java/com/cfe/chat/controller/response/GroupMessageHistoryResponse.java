package com.cfe.chat.controller.response;

import com.cfe.chat.controller.dto.UserMessageDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupMessageHistoryResponse {

    private Integer count;
    private List<GroupMessageHistory> data;
}
