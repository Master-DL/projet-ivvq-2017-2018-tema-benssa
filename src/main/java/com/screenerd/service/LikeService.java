package com.screenerd.service;

import com.screenerd.domain.Like;
import com.screenerd.domain.Post;
import com.screenerd.domain.User;
import com.screenerd.repository.LikeRepository;
import com.screenerd.repository.PostRepository;
import com.screenerd.repository.UserRepository;
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
    private  @Autowired
    UserRepository userRepository;


    public Like saveLike(Like like){
        if(like == null){
            throw new IllegalArgumentException("Like can not be null");
        }
        Post p = like.getPost();
        User user = like.getUser();
        likeRepository.save(like);
        p.addLike(like);
        postRepository.save(p);
        user.addLike(like);
        userRepository.save(user);
        return like;
    }

    public void deleteLike(Long likeId){
        Like toDelete = likeRepository.findOne(likeId);
        if(toDelete == null){
            throw new IllegalArgumentException("Can not delete inexisting like");
        }
        User author = toDelete.getUser();
        Post post  = toDelete.getPost();
        author.removeLike(toDelete);
        post.removeLike(toDelete);
        likeRepository.delete(likeId);
        userRepository.save(author);
        postRepository.save(post);
    }
}
