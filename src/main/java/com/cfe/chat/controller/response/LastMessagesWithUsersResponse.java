package com.cfe.chat.controller.response;

import com.cfe.chat.controller.dto.LastChatMessageDto;
import com.cfe.chat.domain.views.LastChatMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LastMessagesWithUsersResponse {

    private Integer count;
    private List<LastChatMessageDto> data;
}
