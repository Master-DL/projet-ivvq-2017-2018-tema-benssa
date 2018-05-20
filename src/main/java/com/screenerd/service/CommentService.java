package com.screenerd.service;

import com.screenerd.domain.Comment;
import com.screenerd.domain.User;
import com.screenerd.repository.CommentRepository;
import com.screenerd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Benjamin on 13/04/2018.
 */
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment saveComment(Comment comment){

        Comment saved = commentRepository.save(comment);
        comment.getUser().getComments().add(saved);
        comment.getPost().addComment(saved);
        return saved;
    }

    public void deleteComment(Long id) {
        commentRepository.delete(id);
    }
}
