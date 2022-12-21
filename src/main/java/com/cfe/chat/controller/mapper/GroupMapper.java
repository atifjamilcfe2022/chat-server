package com.cfe.chat.controller.mapper;

import com.cfe.chat.controller.dto.GroupDto;
import com.cfe.chat.domain.Group;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    GroupDto toGroupDto(Group group);

    Group toGroup(GroupDto groupDto);
}
