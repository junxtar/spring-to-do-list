package com.sparta.springtodolist.domain.user.service;

import com.sparta.springtodolist.domain.user.entity.User;
import com.sparta.springtodolist.domain.user.exception.ExistsUserException;
import com.sparta.springtodolist.domain.user.repository.UserRepository;
import com.sparta.springtodolist.domain.user.service.dto.request.UserSignupServiceRequestDto;
import com.sparta.springtodolist.domain.user.service.dto.response.UserSignupResponseDto;
import com.sparta.springtodolist.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserSignupResponseDto signup(UserSignupServiceRequestDto requestDto) {
        if (userRepository.existsByUsername(requestDto.getUsername())) {
            throw new ExistsUserException(ErrorCode.EXISTS_USERNAME);
        }
        String password = passwordEncoder.encode(requestDto.getPassword());
        User user = User.create(requestDto.getUsername(), password);

        User saveUser = userRepository.save(user);

        return UserSignupResponseDto.of(saveUser);
    }
}
