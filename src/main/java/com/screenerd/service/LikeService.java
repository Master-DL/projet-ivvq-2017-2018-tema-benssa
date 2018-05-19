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
            throw new IllegalArgumentException("Null Like can not be saved");
        }
        User user = like.getUser();
        Post post = like.getPost();
        likeRepository.save(like);
        user.addLike(like);
        post.addLike(like);
        return like;
    }
}
