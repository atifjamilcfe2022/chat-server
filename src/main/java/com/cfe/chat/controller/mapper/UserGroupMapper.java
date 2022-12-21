package com.cfe.chat.controller.mapper;

import com.cfe.chat.controller.dto.UserGroupDto;
import com.cfe.chat.domain.UserGroup;
import com.cfe.chat.domain.UserGroupMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserGroupMapper {

    UserGroupDto toUserGroupDto(UserGroup userGroup);

    UserGroup toUserGroup(UserGroupDto userGroupDto);
}
