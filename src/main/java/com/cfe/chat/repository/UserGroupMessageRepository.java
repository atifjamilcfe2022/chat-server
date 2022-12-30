package com.cfe.chat.repository;


import com.cfe.chat.domain.Message;
import com.cfe.chat.domain.custom.GroupMessageHistory;
import com.cfe.chat.domain.UserGroup;
import com.cfe.chat.domain.UserGroupMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.OffsetDateTime;
import java.util.List;

public interface UserGroupMessageRepository extends JpaRepository<UserGroupMessage, Long> {

//    @Query("SELECT ugm FROM UserGroupMessage ugm where ugm.userGroup IN (:userGroups) AND ugm.createdAt = :dateTime")
//    List<GroupMessageHistory> findUserMessageHistory(List<UserGroup> userGroups, OffsetDateTime dateTime);

    @Query("SELECT new com.cfe.chat.domain.custom.GroupMessageHistory(m.id, m.messageBody, m.sender, m.createdAt) " +
            "FROM Message m inner join UserGroupMessage ugm ON m.id = ugm.message.id " +
            "where ugm.userGroup IN (:userGroups) " +
            "AND m.createdAt > :dateTime " +
            "group by m.id")
    List<GroupMessageHistory> findUserMessageHistory(List<UserGroup> userGroups, OffsetDateTime dateTime);

    List<UserGroupMessage> findByMessageIn(List<Message> messages);
}
