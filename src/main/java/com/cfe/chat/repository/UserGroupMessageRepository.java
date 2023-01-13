package com.cfe.chat.repository;


import com.cfe.chat.domain.Message;
import com.cfe.chat.domain.custom.GroupMessageHistory;
import com.cfe.chat.domain.UserGroup;
import com.cfe.chat.domain.UserGroupMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface UserGroupMessageRepository extends JpaRepository<UserGroupMessage, Long> {

//    @Query("SELECT ugm FROM UserGroupMessage ugm where ugm.userGroup IN (:userGroups) AND ugm.createdAt = :dateTime")
//    List<GroupMessageHistory> findUserMessageHistory(List<UserGroup> userGroups, OffsetDateTime dateTime);

    @Query("SELECT new com.cfe.chat.domain.custom.GroupMessageHistory(m.id, m.messageBody, m.sender, m.createdAt) " +
            "FROM Message m INNER JOIN UserGroupMessage ugm ON m.id = ugm.message.id " +
            "WHERE ugm.userGroup IN (:userGroups) " +
            "AND m.createdAt > :dateTime " +
            "GROUP BY m.id") // we can remove this group by clause in respect to distinct on m.id like below query
    List<GroupMessageHistory> findUserMessageHistory(List<UserGroup> userGroups, OffsetDateTime dateTime);

    @Query(nativeQuery = true, value = "SELECT distinct m.id, m.message_body, m.user_id, m.created_at " +
            "FROM message m INNER JOIN user_group_message ugm ON m.id = ugm.message_id " +
            "WHERE ugm.user_group_id IN (:userGroups) " +
            "AND m.created_at > :dateTime " +
            "ORDER BY m.id DESC LIMIT 1")
    List<Object[]> getLastMessageSendInGroup(List<Long> userGroups, OffsetDateTime dateTime);

    List<UserGroupMessage> findByMessageIn(List<Message> messages);

    List<UserGroupMessage> findByUserGroupIn(List<UserGroup> userGroups);

    List<UserGroupMessage> findFirstByCreatedAtGreaterThanAndUserGroupInOrderByCreatedAtDesc(OffsetDateTime dateTime, List<UserGroup> userGroups);
}
