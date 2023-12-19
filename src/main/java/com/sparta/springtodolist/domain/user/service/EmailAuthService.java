package com.sparta.springtodolist.domain.user.service;

import com.sparta.springtodolist.domain.user.entity.EmailAuth;
import com.sparta.springtodolist.domain.user.repository.EmailAuthRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailAuthService {

    private final EmailAuthRepository emailAuthRepository;

    public Optional<EmailAuth> findById(String email) {
        return emailAuthRepository.findById(email);
    }

    public Boolean hasMail(String email) {
        return emailAuthRepository.existsById(email);
    }

    public EmailAuth save(EmailAuth emailAuth) {
        return emailAuthRepository.save(emailAuth);
    }

    public void delete(String email) {
        emailAuthRepository.deleteById(email);
    }
}
