package com.cfe.chat.controller.mapper;

import com.cfe.chat.controller.dto.UserMessageDto;
import com.cfe.chat.domain.UserMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMessageMapper {

    UserMessageDto toUserMessageDto(UserMessage userMessage);

    UserMessage toUserMessage(UserMessageDto userMessageDto);
}
