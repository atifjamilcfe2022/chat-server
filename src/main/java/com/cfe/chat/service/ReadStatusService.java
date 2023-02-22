package com.cfe.chat.service;

import com.cfe.chat.domain.Message;
import com.cfe.chat.domain.ReadStatus;
import com.cfe.chat.domain.UserGroup;
import com.cfe.chat.domain.UserGroupMessage;
import com.cfe.chat.exception.DataNotFoundException;
import com.cfe.chat.repository.ReadStatusRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ReadStatusService {
    private final ReadStatusRepository readStatusRepository;

    public ReadStatus findLastReadMessage(UserGroup userGroup) {
        log.debug("finding last read message by user group: {}", userGroup);
        ReadStatus readStatus = readStatusRepository.findByUserGroup(userGroup);
        if (readStatus == null) {
            readStatus = ReadStatus.builder().userGroup(userGroup).message(Message.builder().id(1L).build()).build();
        }
        log.info("found ReadStatus: {}", readStatus);
        return readStatus;
    }

//    public void markAllRead(UserGroup userGroup) {
//        readStatusRepository.markAllRead(userGroup, Boolean.TRUE);
//    }

    public void updateLastReadGroupMessageForUser(UserGroup userGroup, Message message) {
        try {
            ReadStatus readStatus = findLastReadMessage(userGroup);
            readStatus.setMessage(message);
            readStatusRepository.save(readStatus);
        } catch (DataNotFoundException exception) {
            readStatusRepository.save(ReadStatus.builder().userGroup(userGroup).message(message).build());
        }
    }
}
