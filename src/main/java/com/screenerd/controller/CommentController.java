package com.screenerd.controller;

import com.screenerd.domain.Comment;
import com.screenerd.domain.Post;
import com.screenerd.domain.User;
import com.screenerd.repository.PostRepository;
import com.screenerd.service.CommentService;
import com.screenerd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostRepository postRepository;

    @RequestMapping(value = "/api/v1/comment", method = RequestMethod.POST)
    public Comment addComment(@RequestParam(value = "idUser") Long idUser,
                              @RequestParam(value = "idPost") Long idPost,
                              @RequestParam(value = "content") String content) {
        User user = userService.findUser(idUser);
        Post post = postRepository.findOne(idPost);
        Comment toSave = new Comment(content, user, post);
        return commentService.saveComment(toSave);
    }

    @RequestMapping(value = "/api/v1/comment/{id}", method = RequestMethod.DELETE)
    public void deleteComment(@PathVariable("id") Long id) {
        commentService.deleteComment(id);
    }
}
