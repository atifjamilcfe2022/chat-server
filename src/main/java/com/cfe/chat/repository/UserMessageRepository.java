package com.cfe.chat.repository;

import com.cfe.chat.controller.response.UserMessageHistory;
import com.cfe.chat.domain.User;
import com.cfe.chat.domain.UserMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface UserMessageRepository extends JpaRepository<UserMessage, Long> {

    @Query("SELECT new com.cfe.chat.controller.response.UserMessageHistory(" +
            "m.id, m.messageBody, m.sender, um.receiver, um.messageStatus, um.updatedAt) " +
            "FROM Message m inner join UserMessage um ON m.id = um.message.id " +
            "WHERE m.sender IN (:sender, :receiver) AND um.receiver IN (:sender, :receiver) " +
            "AND m.createAt > :dateTime")
    List<UserMessageHistory> findUserMessageHistory(User sender, User receiver, LocalDateTime dateTime);
}
