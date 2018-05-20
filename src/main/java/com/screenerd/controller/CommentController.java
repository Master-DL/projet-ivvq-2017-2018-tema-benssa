package com.screenerd.controller;

import com.screenerd.domain.Comment;
import com.screenerd.domain.Post;
import com.screenerd.domain.User;
import com.screenerd.service.CommentService;
import com.screenerd.service.PostService;
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
    private PostService postService;

    @RequestMapping(value = "api/v1/newComment", method = RequestMethod.POST)
    public Comment addComment(@RequestParam(value = "idUser") Long idUser,
                           @RequestParam(value = "idPost") Long idPost,
                           @RequestParam(value = "content") String content) {
        User user = userService.findUser(idUser);
        Post post = null;
        Iterable<Post> posts = postService.findAllPosts();
        for(Post p : posts){
            if(p.getId() == idPost){
                post = p;
                break;
            }
        }
        return commentService.saveComment(new Comment(content, user, post));
    }

    @RequestMapping(value = "api/v1/deleteComment/{id}", method = RequestMethod.DELETE)
    public void deleteComment(@PathVariable("id") Long id) {
        commentService.deleteComment(id);
    }
}
