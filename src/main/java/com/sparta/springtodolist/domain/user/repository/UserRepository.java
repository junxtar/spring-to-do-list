package com.sparta.springtodolist.domain.user.repository;

import com.sparta.springtodolist.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);
}
