package com.screenerd.service

import com.screenerd.domain.Post
import com.screenerd.domain.User
import com.screenerd.repository.PostRepository
import com.screenerd.repository.UserRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import spock.lang.Specification

/**
 * Created by mathieukostiuk on 27/04/2018.
 */


class PostServiceSpec extends Specification {

    PostService postService
    PostRepository postRepository
    UserRepository userRepository

    void setup() {
        postRepository = Mock()
        userRepository = Mock()
        postService = new PostService()
        postService.postRepository = postRepository
        postService.userRepository = userRepository
    }

    def "check the type of the repository"() {
        expect: "the repository is a Crud Repository"
        postRepository instanceof PagingAndSortingRepository
    }

    def "test save of a Post in the repository"() {
        given: "a post"
        def post = Mock(Post) {
            getUser() >> Mock(User)
        }

        when: "the post is saved"
        postService.savePost(post)

        then: "the save is delegated to the repository"
        1 * postRepository.save(post)
    }

    def "test find a Page"() {
        when: "requesting for all posts"
        postService.findPage(Mock(Pageable))

        then: "the request is delegated to the repository"
        1 * postRepository.findAll(_)
    }

    def "test delete a post" () {
        given: "a existing post with id 1"
        postRepository.findOne(1) >> Mock(Post){
            getUser() >> Mock(User)
        }

        when: "we delete the post"
        postService.deletePost(1)

        then: "the deletion is delegated to the repository"
        1 * postService.postRepository.delete(1)
    }

    def "get one post with its id" () {
        when: "we request the post with id 1"
        postService.findPostById(1)

        then: "the request is delegated to the repository"
        1 * postService.postRepository.findOne(1)
    }

    def "retrieve posts ordered by popularity" () {
        when: "we request posts oredered by popularity"
        postService.findPageOrderedByPopularity(Mock(Pageable))

        then: "the request is delegated to the repository"
        1 * postService.postRepository.findAllByOrderByPopularityDesc(_)
    }

}
