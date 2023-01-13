package com.cfe.chat.repository;

import com.cfe.chat.domain.views.LastChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LastChatMessageViewRepository extends JpaRepository<LastChatMessage, Long> {

    @Query(value = "SELECT id, created_at, updated_at, active, message_body, " +
            "case when sender = :userId then receiver else sender end user_id, " +
            "case when sender = :userId then receiver_name else sender_name end user_name " +
            "from last_chat_messages where sender = :userId or receiver = :userId", nativeQuery = true)
    List<LastChatMessage> getLastChatMessages(Long userId);
}
