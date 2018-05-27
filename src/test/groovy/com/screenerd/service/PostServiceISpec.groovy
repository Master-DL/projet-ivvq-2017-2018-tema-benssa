package com.screenerd.service

import com.screenerd.domain.Post
import com.screenerd.domain.User
import com.screenerd.repository.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import javax.validation.ConstraintViolationException

/**
 * Created by mathieukostiuk on 27/04/2018.
 */

@SpringBootTest
@Transactional
class PostServiceISpec extends Specification {

    @Autowired PostService postService
    @Autowired InitializationService initializationService
    @Autowired PostRepository postRepository

    def "test save a valid post"() {
        given: "a saved user"
        User ben = initializationService.ben
        and: "a valid post"
        Post post = new Post(ben,[0, 0, 0, 0, 0] as byte[], "test", "test")

        when: "the post is saved"
        postService.savePost(post)

        then: "the post has an id after been saved"
        post.id != null
        and: "the user has the post"
        ben.posts.contains(post)
    }

    def "test save a non valid post" () {
        given: "a saved user"
        User ben = initializationService.ben
        and: "a non valid post"
        Post post = new Post(ben,null, "test", "test")

        when: "the post is saved"
        postService.savePost(post)

        then: "a validation exception is thrown"
        thrown ConstraintViolationException
        and: "post has no ID"
        !post.getId()
    }

    def "test save null post"() {
        when: "a null post is saved"
        postService.savePost(null)

        then: "an illegal argument exception is thrown"
        thrown IllegalArgumentException
    }

    def "test find posts for the first page" () {
        given: "a pageable for the first ten posts"
        Pageable pageable = new PageRequest(0,10)

        when: "requesting for all posts"
        Page<Post> page = postService.findPosts(pageable)

        then: "the total number of posts is 4 same as initialization Service number of post"
        page.totalElements == 4
        and: "the result references these 4 posts "
        page.numberOfElements == 4
        and: "the result contains all posts from initialization Service"
        page.content.contains(initializationService.fortniteByThomas)
        page.content.contains(initializationService.pesByThomas)
        page.content.contains(initializationService.catBySarah)
        page.content.contains(initializationService.fifaByBen)
    }

    def "test find posts for the second page" () {
        given: "a pageable for the posts from 10 to 19"
        Pageable pageable = new PageRequest(1, 10)

        when: "requesting for all posts"
        Page<Post> page = postService.findPosts(pageable)

        then: "the total number of posts is 4"
        page.totalElements == 4
        and: "the result references 0 posts"
        page.numberOfElements == 0
    }

    def "test delete existing post" () {
        given: "a saved post"
        Post post = initializationService.fortniteByThomas

        when: "we delete this post"
        postService.deletePost(post.id)

        then: "the post no longer exists"
        !postRepository.findOne(post.id)
        and: "the post is removed from thomas posts"
        !initializationService.thomas.posts.contains(post)
    }

    def "test delete unsaved post" () {
        given: "a unsaved post Id"
        Long postId = Long.MAX_VALUE

        when: "we delete this post"
        postService.deletePost(postId)

        then: "an exception is thrown"
        thrown IllegalArgumentException
    }
}
