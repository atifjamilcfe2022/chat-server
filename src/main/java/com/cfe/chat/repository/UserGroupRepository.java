package com.cfe.chat.repository;

import com.cfe.chat.domain.Group;
import com.cfe.chat.domain.User;
import com.cfe.chat.domain.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

//@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {

//    @Query("SELECT ug FROM UserGroup ug where ug.user = :userId")
//    List<UserGroup> findByUser(Long userId);
//
//    @Query("SELECT ug FROM UserGroup ug where ug.group = :group")
//    List<UserGroup> findByGroup(Group group);

    @Query("SELECT ug FROM UserGroup ug where ug.user = :user")
    List<UserGroup> findUserGroup(User user);

    @Query("SELECT ug FROM UserGroup ug where ug.group = :group")
    List<UserGroup> findUserGroup(Group group);

    @Query("SELECT ug FROM UserGroup ug where ug.user = :user AND ug.group = :group")
    Optional<UserGroup> findByUserGroup(User user, Group group);

    Optional<UserGroup> findByGroupAndUser(Group group, User user);
}
