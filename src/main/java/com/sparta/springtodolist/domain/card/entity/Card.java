package com.sparta.springtodolist.domain.card.entity;

import com.sparta.springtodolist.domain.card.service.dto.request.CardUpdateServiceRequestDto;
import com.sparta.springtodolist.domain.user.entity.User;
import com.sparta.springtodolist.global.util.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Card extends BaseEntity {

    @Id
    @Column(name = "card_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private Boolean isCompleted;
    private Boolean isPrivated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    private Card(String title, String content, Boolean isCompleted, Boolean isPrivated, User user) {
        this.title = title;
        this.content = content;
        this.isCompleted = isCompleted;
        this.isPrivated = isPrivated;
        this.user = user;
    }

    public void update(CardUpdateServiceRequestDto toServiceRequest) {
        this.title = toServiceRequest.getTitle();
        this.content = toServiceRequest.getContent();
    }

    public void updateIsCompleted() {
        this.isCompleted = !this.isCompleted;
    }

    public void updateIsPrivated() {
        this.isPrivated = !this.isPrivated;
    }
}
