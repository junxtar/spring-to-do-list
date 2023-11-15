package com.sparta.springtodolist.domain.user.service;

import com.sparta.springtodolist.domain.user.domain.User;
import com.sparta.springtodolist.domain.user.exception.ExistsUserException;
import com.sparta.springtodolist.domain.user.repository.UserRepository;
import com.sparta.springtodolist.domain.user.service.dto.request.UserSignupServiceRequestDto;
import com.sparta.springtodolist.domain.user.service.dto.response.UserSignupResponseDto;
import com.sparta.springtodolist.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserSignupResponseDto signup(UserSignupServiceRequestDto requestDto) {
        User user = User.create(requestDto.getUsername(), requestDto.getPassword());

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new ExistsUserException(ErrorCode.EXISTS_USERNAME);
        }
        User saveUser = userRepository.save(user);

        return UserSignupResponseDto.of(saveUser);

    }
}
