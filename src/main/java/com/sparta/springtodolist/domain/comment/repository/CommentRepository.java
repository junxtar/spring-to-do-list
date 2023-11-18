package com.sparta.springtodolist.domain.comment.repository;


import com.sparta.springtodolist.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
