package com.screenerd.service;

import com.screenerd.domain.Like;
import com.screenerd.repository.LIkeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by telly on 13/04/18.
 */
@Service
public class LikeService {

    private @Autowired
    LIkeRepository lIkeRepository;

    public  void saveLike(Like like){
        lIkeRepository.save(like);
    }
}
