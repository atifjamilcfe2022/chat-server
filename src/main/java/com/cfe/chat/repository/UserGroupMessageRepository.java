package com.cfe.chat.repository;


import com.cfe.chat.controller.response.GroupMessageHistory;
import com.cfe.chat.domain.Message;
import com.cfe.chat.domain.User;
import com.cfe.chat.domain.UserGroup;
import com.cfe.chat.domain.UserGroupMessage;
import com.cfe.chat.enums.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface UserGroupMessageRepository extends JpaRepository<UserGroupMessage, Long> {

//    @Query("SELECT ugm FROM UserGroupMessage ugm where ugm.userGroup IN (:userGroups) AND ugm.createdAt = :dateTime")
//    List<GroupMessageHistory> findUserMessageHistory(List<UserGroup> userGroups, LocalDateTime dateTime);

    @Query("SELECT new com.cfe.chat.controller.response.GroupMessageHistory(m.id, m.messageBody, m.sender) " +
            "FROM UserGroupMessage ugm inner join Message m ON ugm.message.id = m.id " +
            "where ugm.userGroup IN (:userGroups) " +
            "AND ugm.createdAt > :dateTime " +
            "group by ugm.message")
    List<GroupMessageHistory> findUserMessageHistory(List<UserGroup> userGroups, LocalDateTime dateTime);
}
