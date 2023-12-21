package com.sparta.springtodolist.domain.comment.repository;


import com.sparta.springtodolist.domain.card.entity.Card;
import com.sparta.springtodolist.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    public void deleteByCard(Card card);
}
