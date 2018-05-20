package com.screenerd.service

import com.screenerd.domain.Post
import com.screenerd.domain.User
import org.hibernate.exception.ConstraintViolationException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by mathieukostiuk on 27/04/2018.
 */

@SpringBootTest
@Transactional
class PostServiceISpec extends Specification {

    @Autowired PostService postService


    def "test save a valid post"() {
        given: "a user"
        User user = new User("test", "testtesttesttesttest", [0, 0, 0, 0, 0] as byte[]);

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

    def "findAll Posts" () {
        given: "one valid User"
        def initSize = postService.findAllPosts().size()
        User user = new User("test", "testtesttesttesttest", [0, 0, 0, 0, 0] as byte[])

        and: "two posts this user wrote"
        Post post1 = new Post(user,[0, 0, 0, 0, 0] as byte[], "test1", "test1")
        Post post2 = new Post(user,[0, 0, 0, 0, 0] as byte[], "test2", "test2")

        and: "we save the 2 posts"
        postService.savePost(post1)
        postService.savePost(post2)

        when: "requesting for all posts"
        ArrayList<Post> posts = postService.findAllPosts()

        then: "the result references 2 posts"
        posts.size() == initSize + 2
    }

    def "delete one post" () {
        given: "one valid User"
        def initSize = postService.findAllPosts().size()
        User user = new User("test", "testtesttesttesttest", [0, 0, 0, 0, 0] as byte[])

        and: "two posts this user wrote"
        Post post1 = new Post(user,[0, 0, 0, 0, 0] as byte[], "test1", "test1")
        Post post2 = new Post(user,[0, 0, 0, 0, 0] as byte[], "test2", "test2")

        and: "we save the 2 posts"
        post1 = postService.savePost(post1)
        post2 = postService.savePost(post2)

        when: "we delete the first post"
        postService.deletePost(post1.getId())

        and: "requesting for all posts"
        ArrayList<Post> posts = postService.findAllPosts()

        then: "the result references 1 post"
        posts.size() == initSize + 1

    }

    def "retrieve one post with its id" () {
        given: "one valid user"
        User user = new User("test", "testtesttesttesttest", [0, 0, 0, 0, 0] as byte[])

        and: "a post this user wrote"
        Post post1 = new Post(user,[0, 0, 0, 0, 0] as byte[], "test1", "test1")

        and: "this post is saved in the repo"
        post1 = postService.savePost(post1)
        def id = post1.getId()

        when: "we request this post with its id"
        def retrivedPost = postService.findPostById(id)

        then: "the retrievedPost is not null and has the same description"
        retrivedPost != null
        retrivedPost.description == post1.description
    }
}
