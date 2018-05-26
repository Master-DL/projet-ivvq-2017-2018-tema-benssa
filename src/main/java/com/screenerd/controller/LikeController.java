package com.screenerd.controller;

import com.screenerd.domain.Like;
import com.screenerd.domain.Post;
import com.screenerd.domain.User;
import com.screenerd.repository.PostRepository;
import com.screenerd.repository.UserRepository;
import com.screenerd.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by telly on 18/05/18.
 */
@RestController
public class LikeController {

    @Autowired
    private LikeService likeService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;


    @RequestMapping(value = "/api/v1/like",method = RequestMethod.POST)
    public Like addLike(@RequestParam(value = "userId") Long userId,@RequestParam(value = "postId") Long postId,
                         @RequestParam(value = "value") int value){
        User user = userRepository.findOne(userId);
        Post post = postRepository.findOne(postId);
        if(user == null || post == null){
            throw new IllegalArgumentException("Can not save like with unsaved user/post");
        }
        Like like = new Like(value,user,post);
        return likeService.saveLike(like);
    }
}
