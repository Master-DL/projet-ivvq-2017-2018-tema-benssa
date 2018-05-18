package com.screenerd.repository;

import com.screenerd.domain.Comment;
import com.screenerd.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Benjamin on 13/04/2018.
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
}