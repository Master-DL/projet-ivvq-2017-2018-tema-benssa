package com.screenerd.service

import com.screenerd.domain.Post
import com.screenerd.repository.PostRepository
import org.springframework.data.repository.CrudRepository
import spock.lang.Specification

/**
 * Created by mathieukostiuk on 27/04/2018.
 */

class PostServiceSpec extends Specification {

    PostService postService;
    PostRepository postRepository;

    void setup() {
        postRepository = Mock();
        postService = new PostService();
        postService.postRepository = postRepository;
    }

    def "check the type of the repository"() {
        expect: "the repository is a Crud Repository"
        postRepository instanceof CrudRepository
    }

    def "test save of a Post in the repository"() {
        given: "a post"
        def post = Mock(Post)

        when: "the post is saved"
        postService.savePost(post)

        then: "the save is delegated to the repository"
        1 * postRepository.save(post)
    }
}
