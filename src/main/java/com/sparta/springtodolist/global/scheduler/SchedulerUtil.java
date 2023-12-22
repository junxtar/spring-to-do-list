package com.sparta.springtodolist.global.scheduler;

import com.sparta.springtodolist.domain.card.entity.Card;
import com.sparta.springtodolist.domain.card.repository.CardRepository;
import com.sparta.springtodolist.domain.comment.repository.CommentRepository;
import com.sparta.springtodolist.infra.s3.util.S3Util;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j(topic = "Scheduler: ")
public class SchedulerUtil {

    private final S3Util s3Util;
    private final CardRepository cardRepository;
    private final CommentRepository commentRepository;

    @Scheduled(cron = "1 0 0 * * *") //매일 오전 0시 00분 01초
    public void deleteCard() {
        List<Card> cards = cardRepository.findAll();
        LocalDateTime date = LocalDateTime.now();
        for (Card card : cards) {
            if (date.isAfter(card.getModifiedAt().plusDays(90))) {
                commentRepository.deleteByCard(card);
                s3Util.deleteImage(card.getImageName(), S3Util.IMAGE_PATH);
                cardRepository.delete(card);
            }
        }
    }
}
