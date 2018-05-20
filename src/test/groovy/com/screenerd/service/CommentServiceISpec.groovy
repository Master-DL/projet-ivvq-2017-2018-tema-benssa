package com.screenerd.service

import com.screenerd.domain.Comment
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

    def "delete one comment" () {
        given: "an user"
        User user = new User("test", "testtesttesttesttest", [0, 0, 0, 0, 0] as byte[])
        and: "a post"
        Post post = new Post(user: user, description: "GG", image: [0, 0, 0, 0, 0] as byte[] , imageFormat: "png");
        and: "two comment this user wrote on this post"
        Comment comment1 = new Comment(content:"comment 1 here", user: user, post: post);
        Comment comment2 = new Comment(content:"comment 2 here", user: user, post: post);

        and: "we save the 2 comments"
        postService.savePost(post)
        userService.saveUser(user)
        comment1 = commentService.saveComment(comment1)
        comment2 = commentService.saveComment(comment2)

        when: "we delete the first comment"
        commentService.deleteComment(comment1.getId())

        and: "requesting for all comments of this post"
        ArrayList<Comment> comments = user.getComments();

        then: "the result references 1 post"
        comments.size() == 1

    }
}