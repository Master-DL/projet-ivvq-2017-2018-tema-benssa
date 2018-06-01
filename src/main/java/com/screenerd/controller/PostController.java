package com.screenerd.controller;

import com.screenerd.domain.Like;
import com.screenerd.domain.Post;
import com.screenerd.domain.User;
import com.screenerd.service.PostService;
import com.screenerd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;



@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "api/v1/post", method = RequestMethod.POST)
    public Post addPost(@RequestParam(value = "idUser") Long idUser,
                        @RequestParam(value = "image") byte[] image,
                        @RequestParam(value = "imageFormat") String imageFormat,
                        @RequestParam(value = "description") String description) {
        User user = userService.findUser(idUser);
        if (user == null)
            throw new IllegalArgumentException("User must exists");
        Post post = new Post(user, image, imageFormat, description);
        return postService.savePost(post);
    }

    @RequestMapping(value = "api/v1/post/{id}", method = RequestMethod.DELETE)
    public void deletePost(@PathVariable("id") Long id) {
        postService.deletePost(id);
    }

    @RequestMapping(value = "api/v1/post", method = RequestMethod.GET)
    public Page<Post> findPosts(Pageable pageable) {
        return postService.findPage(pageable);
    }

    @RequestMapping(value = "api/v1/post/popularity", method = RequestMethod.GET)
    public Page<Post> findPostsOrderedByPopularity(Pageable p) {
        return postService.findPageOrderedByPopularity(p);
    }
}
