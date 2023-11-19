package com.sparta.springtodolist.domain.card.repository;

import com.sparta.springtodolist.domain.card.entity.Card;
import com.sparta.springtodolist.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findAllByOrderByCreatedAtDesc();
    List<Card> findByUser(User user);
    List<Card> findCardsByTitleContaining(String title);
}
