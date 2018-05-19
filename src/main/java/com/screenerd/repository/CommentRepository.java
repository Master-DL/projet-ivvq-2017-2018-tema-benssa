package com.screenerd.repository;

import com.screenerd.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Created by Benjamin on 13/04/2018.
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
}