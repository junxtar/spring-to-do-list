package com.sparta.springtodolist.domain.user.service;

import com.sparta.springtodolist.domain.user.domain.User;
import com.sparta.springtodolist.domain.user.exception.ExistsUserException;
import com.sparta.springtodolist.domain.user.repository.UserRepository;
import com.sparta.springtodolist.domain.user.service.dto.request.UserSignupServiceRequestDto;
import com.sparta.springtodolist.domain.user.service.dto.response.UserSignupResponseDto;
import com.sparta.springtodolist.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
