package com.screenerd.service;

import com.screenerd.domain.Comment;
import com.screenerd.domain.Post;
import com.screenerd.domain.User;
import com.screenerd.repository.CommentRepository;
import com.screenerd.repository.PostRepository;
import com.screenerd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        User author = comment.getUser();
        author.addComment(saved);
        userRepository.save(author);
        Post post = comment.getPost();
        post.addComment(saved);
        postRepository.save(post);
        return saved;
    }

    public void deleteComment(Long id){
        Comment c = commentRepository.findOne(id);
        Post post = c.getPost();
        post.removeComment(c);
        User user = c.getUser();
        user.removeComment(c);
        userRepository.save(user);
        postRepository.save(post);
        commentRepository.delete(id);
    }

}
