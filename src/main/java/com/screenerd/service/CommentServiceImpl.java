package com.screenerd.service;

import com.screenerd.domain.Comment;
import com.screenerd.domain.Post;
import com.screenerd.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Benjamin on 13/04/2018.
 */
@Repository
@Transactional(readOnly = true)
class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository repository;

    @Override
    @Transactional
    public Comment save(Comment comment) {
        return repository.save(comment);
    }

}