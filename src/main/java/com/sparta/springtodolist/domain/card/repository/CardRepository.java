package com.sparta.springtodolist.domain.card.repository;

import com.sparta.springtodolist.domain.card.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {

}
