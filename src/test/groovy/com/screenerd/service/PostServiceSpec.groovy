package com.screenerd.service

import com.screenerd.domain.Post
import com.screenerd.domain.User
import com.screenerd.repository.PostRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import spock.lang.Specification

/**
 * Created by mathieukostiuk on 27/04/2018.
 */

class PostServiceSpec extends Specification {

    PostService postService
    PostRepository postRepository
    User user
    Post post

    void setup() {
        postRepository = Mock()
        postService = new PostService()
        postService.postRepository = postRepository
    }

    def "check the type of the repository"() {
        expect: "the repository is a Paging And Sorting Repository"
        postRepository instanceof PagingAndSortingRepository
    }

    def "test save of post is delegated to the post Repository"() {
        given: "a post"
        def post = Mock(Post) {
            getUser() >> Mock(User)
        }

        when: "the post is saved"
        postService.savePost(post)

        then: "the save is delegated to the repository"
        1 * postRepository.save(post)
    }

    def "test find posts by page is delegated to the post Repository"() {
        when: "requesting for posts with a page"
        postService.findPosts(Mock(Pageable))

        then: "the request is delegated to the repository"
        1 * postRepository.findAll(_)
    }

    def "test delete a post is delegated to the post Repository" () {
        given: "the repo has one post with id 1"
        postRepository.findOne(1) >> Mock(Post){
            getUser() >> Mock(User)
        }

        when: "we delete the post"
        postService.deletePost(1)

        then: "the deletion is delegated to the repository"
        1 * postService.postRepository.delete(1)
    }
}
