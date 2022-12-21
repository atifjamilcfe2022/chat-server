package com.cfe.chat.controller.mapper;

import com.cfe.chat.controller.dto.GroupDto;
import com.cfe.chat.controller.dto.MessageDto;
import com.cfe.chat.domain.Group;
import com.cfe.chat.domain.Message;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    MessageDto toMessageDto(Message message);

    Message toMessage(MessageDto messageDto);
}
