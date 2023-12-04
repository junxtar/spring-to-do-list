package com.sparta.springtodolist.domain.card.entity;

import com.sparta.springtodolist.domain.comment.entity.Comment;
import com.sparta.springtodolist.domain.user.entity.User;
import com.sparta.springtodolist.global.util.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
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
    private Boolean isPublic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "card", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    private Card(Long id, String title, String content, Boolean isCompleted, Boolean isPublic, User user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.isCompleted = isCompleted;
        this.isPublic = isPublic;
        this.user = user;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void updateIsCompleted() {
        this.isCompleted = !this.isCompleted;
    }

    public void updateIsPublic() {
        this.isPublic = !this.isPublic;
    }
}
