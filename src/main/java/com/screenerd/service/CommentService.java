package com.screenerd.service;

import com.screenerd.domain.Comment;
import com.screenerd.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Benjamin on 13/04/2018.
 */
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment saveComment(Comment comment){
        Comment saved = commentRepository.save(comment);
        //comment.getUser().getComments().add(saved);
        //comment.getPost().addComment(saved);
        return saved;
    }
}
