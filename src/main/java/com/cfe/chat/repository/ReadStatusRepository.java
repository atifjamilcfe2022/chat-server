package com.cfe.chat.repository;

import com.cfe.chat.domain.ReadStatus;
import com.cfe.chat.domain.User;
import com.cfe.chat.domain.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReadStatusRepository extends JpaRepository<ReadStatus, Long> {

//    @Query("SELECT COUNT(rs) FROM ReadStatus rs WHERE rs.userGroup = :userGroup AND readValue = :readValue")
//    Long findUnreadMessages(UserGroup userGroup, Boolean readValue);
//    @Modifying
//    @Query("UPDATE ReadStatus rs SET rs.readValue = :readValue WHERE rs.userGroup = :userGroup")
//    void markAllRead(@Param("userGroup") UserGroup userGroup, @Param("readValue") Boolean readValue);

    ReadStatus findByUserGroup(UserGroup userGroup);
}
