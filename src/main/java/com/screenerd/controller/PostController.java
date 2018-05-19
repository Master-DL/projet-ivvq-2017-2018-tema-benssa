package com.screenerd.controller;

import com.screenerd.domain.Post;
import com.screenerd.domain.User;
import com.screenerd.service.PostService;
import com.screenerd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "api/v1/newPost", method = RequestMethod.POST)
    public Post addPost(@RequestParam(value = "idUser") Long idUser,
                        @RequestParam(value = "image") byte[] image,
                        @RequestParam(value = "imageFormat") String imageFormat,
                        @RequestParam(value = "description") String description) {
        User user = userService.getUser(idUser);
        return postService.savePost(new Post(user,image,imageFormat,description));
    }
}
