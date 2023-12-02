package com.sparta.springtodolist.domain.user.entity;

import com.sparta.springtodolist.global.util.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "users")
public class User extends BaseEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @Builder
    private User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public static User create(String username, String password) {
        return User.builder()
            .username(username)
            .password(password)
            .build();
    }
}
