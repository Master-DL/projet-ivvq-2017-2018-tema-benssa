package com.screenerd.service

import com.screenerd.domain.Comment
import org.springframework.boot.test.context.SpringBootTest

import javax.validation.ConstraintViolationException
import com.screenerd.domain.Post
import com.screenerd.domain.User
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@SpringBootTest
class CommentServiceISpec extends Specification {

    @Autowired
    private CommentService commentService
    @Autowired
    private UserService userService
    @Autowired
    private PostService postService

    def "test save a valid comment"(){
        given: "a user"
        User user = new User(login: "login",password: "password",avatar: [1,3,6]);
        and: "a post"
        Post post = new Post(user: user, description: "GG", image: [0, 0, 0, 0, 0] as byte[] , imageFormat: "png");
        and: "a comment"
        Comment comment = new Comment(content:"a comment", user: user, post: post);

        when: "the comment is saved"
        User savedUser = userService.saveUser(user)
        Post savedPost = postService.savePost(post)
        user.getComments().add(comment)
        Comment savedComment = commentService.saveComment(comment)

        then: "the comment has an id"
        comment.id != null
    }

    def "test save a non valid user"(){
        given: "a user"
        User user = new User(login: "login",password: "password",avatar: [1,3,6]);
        and: "a post"
        Post post = new Post(user: user, description: "GG", image: [0, 0, 0, 0, 0] as byte[] , imageFormat: "png");
        and: "a comment"
        Comment comment = new Comment(content:"a", user: null, post: post);

        when: "the user is saved"
        User savedUser = userService.saveUser(user)
        Post savedPost = postService.savePost(post)
        user.getComments().add(comment)
        Comment savedComment = commentService.saveComment(comment)

        then: "A validation exception is thrown"
        thrown ConstraintViolationException

        and: "the user has still a null id"
        comment.id == null
    }
}