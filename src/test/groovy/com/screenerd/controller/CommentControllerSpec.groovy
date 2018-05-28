package com.screenerd.controller

import com.screenerd.domain.Comment
import com.screenerd.domain.Post
import com.screenerd.domain.User
import com.screenerd.repository.PostRepository
import com.screenerd.repository.UserRepository
import com.screenerd.service.CommentService
import com.screenerd.service.LikeService
import spock.lang.Specification

/**
 * Created by telly on 19/05/18.
 */
class CommentControllerSpec extends Specification{

    CommentController commentController
    CommentService commentService
    UserRepository userRepository
    PostRepository postRepository

    def setup(){
        commentService = Mock()
        userRepository = Mock()
        postRepository = Mock()
        commentController = new CommentController()
        commentController.commentService = commentService
    }

    def "test add comment"(){

        given: "a user"
        User user = new User(login: "login",password: "password",avatar: [1,3,6]);
        User savedUser = userRepository.save(user);
        and: "a post"
        Post post = new Post(user: user, description: "GG", image: [0, 0, 0, 0, 0] as byte[] , imageFormat: "png");
        Post savedPost = postRepository.save(post);

        when: "the add comment URL is triggered"
        commentController.addComment(savedUser.getId(),savedPost.getId(),"ceci est un commentaire")

        then: "the save is delegated to the commentService"
        1 * commentService.saveComment(_)
    }

}
