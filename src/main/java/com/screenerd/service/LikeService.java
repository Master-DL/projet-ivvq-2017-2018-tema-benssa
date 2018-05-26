package com.screenerd.service;

import com.screenerd.domain.Like;
import com.screenerd.domain.Post;
import com.screenerd.domain.User;
import com.screenerd.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by telly on 13/04/18.
 */
@Service
public class LikeService {

    private @Autowired
    LikeRepository likeRepository;


    public Like saveLike(Like like){
        if(like == null){
            throw new IllegalArgumentException("Like can not be null");
        }
        User user = like.getUser();
        if(user.getId() == null){
            throw new IllegalArgumentException("Like can not be saved with unsaved user");
        }
        Post post = like.getPost();
        if(post.getId() == null){
            throw new IllegalArgumentException("Like can not be saved with unsaved Post");
        }
        likeRepository.save(like);
        user.addLike(like);
        post.addLike(like);
        return like;
    }
}
