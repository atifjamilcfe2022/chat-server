package com.cfe.chat.repository;

import com.cfe.chat.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByCreateAtBefore(ZonedDateTime createAt);
}
