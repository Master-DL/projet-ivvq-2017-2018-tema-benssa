package com.screenerd.service

import com.screenerd.domain.Comment
import com.screenerd.repository.CommentRepository
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

import javax.validation.ConstraintViolationException
import com.screenerd.domain.Post
import com.screenerd.domain.User
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Transactional
@SpringBootTest
class CommentServiceISpec extends Specification {

    @Autowired
    private CommentService commentService
    @Autowired
    private UserService userService
    @Autowired
    private PostService postService
    @Autowired
    private CommentRepository commentRepository

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
        Comment savedComment = commentService.saveComment(comment)

        then: "the comment has an id"
        comment.id != null
        and: "the user have the comment"
        user.getComments().contains(savedComment)
        and: "the post have the comment"
        post.getComments().contains(savedComment)

    }

    def "test save a non valid comment"(){
        given: "a user"
        User user = new User(login: "login",password: "password",avatar: [1,3,6]);
        and: "a post"
        Post post = new Post(user: user, description: "GG", image: [0, 0, 0, 0, 0] as byte[] , imageFormat: "png");
        and: "a comment"
        Comment comment = new Comment(content:"a", user: null, post: post);

        when: "the comment is saved"
        User savedUser = userService.saveUser(user)
        Post savedPost = postService.savePost(post)
        Comment savedComment = commentService.saveComment(comment)

        then: "A validation exception is thrown"
        thrown ConstraintViolationException

        and: "the comment has still a null id"
        comment.id == null
    }

    def "test delete a saved comment"(){
        given: "a user"
        User user = new User(login: "login",password: "password",avatar: [1,3,6]);
        and: "a post"
        Post post = new Post(user: user, description: "GG", image: [0, 0, 0, 0, 0] as byte[] , imageFormat: "png");
        and: "a comment"
        Comment comment = new Comment(content:"ceci est un commentaire", user: user, post: post);

        when: "the like with this id is deleted"
        User savedUser = userService.saveUser(user)
        Post savedPost = postService.savePost(post)
        Comment savedComment = commentService.saveComment(comment)
        Long id = comment.getId();
        commentService.deleteComment(id)

        then: "the like no longer exists"
        !commentRepository.findOne(id)
        and: "the user do not longer have the comment"
        !user.getComments().contains(savedComment)
        and: "the post do not longer have the comment"
        !post.getComments().contains(savedComment)

    }
}