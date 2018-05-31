package com.screenerd.service;

import com.screenerd.domain.Like;
import com.screenerd.domain.Post;
import com.screenerd.domain.User;
import com.screenerd.repository.LikeRepository;
import com.screenerd.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by telly on 13/04/18.
 */
@Service
public class LikeService {

    private @Autowired
    LikeRepository likeRepository;
    private @Autowired
    PostRepository postRepository;


    public Like saveLike(Like like){
        if(like == null){
            throw new IllegalArgumentException("Like can not be null");
        }
        User user = like.getUser();
        Post post = like.getPost();
        likeRepository.save(like);
        user.addLike(like);
        post.addLike(like);
        postRepository.save(post);
        return like;
    }

    public void deleteLike(Long likeId){
        Like toDelete = likeRepository.findOne(likeId);
        if(toDelete == null){
            throw new IllegalArgumentException("Can not delete inexisting like");
        }
        toDelete.getUser().removeLike(toDelete);
        toDelete.getPost().removeLike(toDelete);
        likeRepository.delete(likeId);
    }
}
