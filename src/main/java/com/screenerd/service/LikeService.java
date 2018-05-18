package com.screenerd.service;

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

}
