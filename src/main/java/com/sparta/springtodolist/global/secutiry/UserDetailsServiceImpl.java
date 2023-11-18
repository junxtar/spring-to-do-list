package com.sparta.springtodolist.global.secutiry;

import static com.sparta.springtodolist.global.exception.ErrorCode.NOT_FOUND_USERNAME;

import com.sparta.springtodolist.domain.user.entity.User;
import com.sparta.springtodolist.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(NOT_FOUND_USERNAME.getMessage()));

        return new UserDetailsImpl(user);
    }
}