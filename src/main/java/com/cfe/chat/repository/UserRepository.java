package com.cfe.chat.repository;

import com.cfe.chat.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE deleted IS NULL OR deleted != 'true'")
    List<User> findActiveUsers();

}
