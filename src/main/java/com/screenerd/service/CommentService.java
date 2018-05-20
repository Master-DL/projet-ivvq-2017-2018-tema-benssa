package com.screenerd.service;

import com.screenerd.domain.Comment;
import com.screenerd.domain.Post;
import com.screenerd.domain.User;
import com.screenerd.repository.CommentRepository;
import com.screenerd.repository.PostRepository;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;
    public Comment saveComment(Comment comment){
        Comment saved = commentRepository.save(comment);
        comment.getUser().getComments().add(saved);
        userRepository.save(saved.getUser());
        comment.getPost().addComment(saved);
        postRepository.save(saved.getPost());
        return saved;
    }

    public void deleteComment(Long id) {
        Comment comment = commentRepository.findOne(id);
        User user = userRepository.findById(comment.getUser().getId());
        user.getComments().remove(comment);
        commentRepository.delete(id);
    }
}
