package com.cfe.chat.repository;

import com.cfe.chat.domain.Message;
import com.cfe.chat.domain.custom.UserMessageHistory;
import com.cfe.chat.domain.User;
import com.cfe.chat.domain.UserMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.OffsetDateTime;
import java.util.List;

public interface UserMessageRepository extends JpaRepository<UserMessage, Long> {

    @Query("SELECT new com.cfe.chat.domain.custom.UserMessageHistory(" +
            "m.id, m.messageBody, m.sender, um.receiver, um.messageStatus, um.updatedAt) " +
            "FROM Message m inner join UserMessage um ON m.id = um.message.id " +
            "WHERE (m.sender = :sender AND um.receiver = :receiver) OR (m.sender = :receiver AND um.receiver = :sender) " +
            "AND m.createAt > :dateTime")
    List<UserMessageHistory> findUserMessageHistory(User sender, User receiver, OffsetDateTime dateTime);

    @Query("SELECT um FROM UserMessage um WHERE um.id IN " +
            "(SELECT MAX(um1.id) FROM UserMessage um1 where um1.message IN " +
            "(SELECT m FROM Message m WHERE m.sender = :sender) " +
            "GROUP BY um1.receiver)")
    List<UserMessage> findRecipientChatWithUser(User sender);

    List<UserMessage> findByMessageIn(List<Message> messages);
}
