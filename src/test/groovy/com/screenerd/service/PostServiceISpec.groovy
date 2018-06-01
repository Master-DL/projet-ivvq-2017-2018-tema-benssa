package com.screenerd.service

import com.screenerd.domain.Like
import com.screenerd.domain.Post
import com.screenerd.domain.User
import com.screenerd.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

/**
 * Created by mathieukostiuk on 27/04/2018.
 */

@SpringBootTest
@Transactional
class PostServiceISpec extends Specification {

    @Autowired
    PostService postService
    @Autowired
    InitializationService initializationService
    @Autowired
    LikeService likeService
    @Autowired
    UserRepository userRepository

    def "test save a valid post"() {
        given: "a user"
        User user = initializationService.ben

        and: "a valid post"
        Post post = new Post(user,[0, 0, 0, 0, 0] as byte[], "test", "test");

        when: "the post is saved"
        postService.savePost(post)

        then: "the post has an id after been saved"
        post.id != null

        and: "the user test has also an id"
        user.id != null

        and: "the user test has a post"
        user.getPosts().size() == 1
        user.getPosts().first().getDescription() == post.getDescription()

    }

    def "test a non valid post" () {
        given: "a non valid post"
        Post post = new Post(null,[0, 0, 0, 0, 0] as byte[], "test", "test")

        when: "the post is saved"
        postService.savePost(post)

        then: "a validation exception is thrown"
        thrown javax.validation.ConstraintViolationException

        and: "post has no ID"
        !post.getId()
    }

    def "test find posts for the first page" () {
        given: "a pageable for the first ten posts"
        Pageable pageable = new PageRequest(0,10)

        when: "requesting for all posts"
        Page<Post> page = postService.findPage(pageable)

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
        Page<Post> page = postService.findPage(pageable)

        then: "the total number of posts is 4"
        page.totalElements == 4
        and: "the result references 0 posts"
        page.numberOfElements == 0
    }


    def "delete one post" () {
        given: "one valid Post"
        Post post = initializationService.fortniteByThomas

        when: "we delete the post"
        postService.deletePost(post.id)

        and: "requesting for the post"
        Post fetchedpost = postService.findPostById(post.id)

        then: "the result references 1 post"
        fetchedpost == null
        and:"the post is removed from thomas posts"
        !initializationService.thomas.posts.contains(post)
    }

    def "retrieve one post with its id" () {
        given: "one valid post"
        Post post = initializationService.fortniteByThomas

        when: "we request this post with its id"
        def retrivedPost = postService.findPostById(post.id)

        then: "the retrievedPost is not null and has the same description"
        retrivedPost != null
        retrivedPost.description == post.description
    }

    def "retrieve posts ordered by popularity" () {
        given: ""
        Post p1 = postService.findPostById(initializationService.fortniteByThomas.id)
        Post p2 = postService.findPostById(initializationService.pesByThomas.id)
        Post p3 = postService.findPostById(initializationService.catBySarah.id)
        Post p4 = postService.findPostById(initializationService.fifaByBen.id)

        expect: "posts have correct popylarity"
        p1.popularity == 3
        p2.popularity == 4
        p3.popularity == 2
        p4.popularity == 1

        when: "we retrieve the posts ordered by popularity"
        Pageable pageable = new PageRequest(0, 10)
        Page<Post> posts = postService.findPageOrderedByPopularity(pageable)

        then: "the first post is post1"
        posts.asList().get(0) == p2
        posts.asList().get(1) == p1
        posts.asList().get(2) == p3
        posts.asList().get(3) == p4
    }
}
