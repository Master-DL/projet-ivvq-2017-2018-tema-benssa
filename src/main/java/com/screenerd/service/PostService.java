package com.screenerd.service;

import com.screenerd.domain.Post;
import com.screenerd.domain.User;
import com.screenerd.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by mathieukostiuk on 27/04/2018.
 */
@Service
public class PostService {
    @Autowired private PostRepository postRepository;

    public Post savePost(Post post) {
        if (post == null)
            throw new IllegalArgumentException("Post cannot be null");
        postRepository.save(post);
        User author = post.getUser();
        author.getPosts().add(post);
        return post;
    }

    public PostRepository getPostRepository() {
        return this.postRepository;
    }

    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Iterable<Post> findAllPosts() {
        return this.postRepository.findAll();
    }
}