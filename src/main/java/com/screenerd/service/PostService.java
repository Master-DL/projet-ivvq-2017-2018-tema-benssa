package com.screenerd.service;


import com.screenerd.domain.Post;
import com.screenerd.domain.User;
import com.screenerd.repository.PostRepository;
import com.screenerd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by mathieukostiuk on 27/04/2018.
 */
@Service
public class PostService {
    @Autowired private PostRepository postRepository;
    @Autowired private UserService userService;

    public Post savePost(Post post) {
        if (post == null)
            throw new IllegalArgumentException("Post cannot be null");
        User author = post.getUser();
        if (author != null) {
            if (author.getId() == null)
                userService.saveUser(author);
            author.getPosts().add(post);
        }
        return postRepository.save(post);
    }

    public void deletePost(Long id) {
        Post post = this.postRepository.findOne(id);
        post.getUser().getPosts().remove(post);
        this.postRepository.delete(id);
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
