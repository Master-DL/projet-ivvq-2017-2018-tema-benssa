package com.screenerd.service;

import com.screenerd.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by telly on 17/05/18.
 */
@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
}
