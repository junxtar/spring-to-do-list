package com.sparta.springtodolist.domain.user.repository;

import com.sparta.springtodolist.domain.user.entity.EmailAuth;
import org.springframework.data.repository.CrudRepository;

public interface EmailAuthRepository extends CrudRepository<EmailAuth, String> {
}
