package com.screenerd.service;

import com.screenerd.domain.Comment;
import com.screenerd.domain.Post;
import com.screenerd.repository.CommentRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Benjamin on 13/04/2018.
 */
public interface CommentService {

    @Transactional
    Comment save(Comment comment);
}
