package com.sparta.springtodolist.domain.card.repository;

import com.sparta.springtodolist.domain.card.entity.Card;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findAllBy(Pageable pageable);
    List<Card> findAllByOrderByCreatedAtDesc();
    List<Card> findCardsByTitleContaining(String title);
    void deleteAllByUser_Id(Long id);
    List<Card> findByUser_Id(Long id);

}
