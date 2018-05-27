package com.screenerd.service;


import com.screenerd.domain.Post;
import com.screenerd.domain.User;
import com.screenerd.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        User author = post.getUser();
        postRepository.save(post);
        author.addPost(post);
        return post;

    }

    public void deletePost(Long id) {
        Post post = postRepository.findOne(id);
        if(post == null){
            throw new IllegalArgumentException("Can not delete inexisting post");
        }
        post.getUser().removePost(post);
        postRepository.delete(id);
    }

    public Page<Post> findPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

}
