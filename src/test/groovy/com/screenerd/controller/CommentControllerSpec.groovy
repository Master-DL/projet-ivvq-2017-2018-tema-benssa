package com.screenerd.controller

import com.screenerd.domain.Comment
import com.screenerd.domain.Post
import com.screenerd.domain.User
import com.screenerd.repository.PostRepository
import com.screenerd.repository.UserRepository
import com.screenerd.service.CommentService
import com.screenerd.service.LikeService
import com.screenerd.service.PostService
import com.screenerd.service.UserService
import spock.lang.Specification

/**
 * Created by telly on 19/05/18.
 */
class CommentControllerSpec extends Specification{

    CommentController commentController
    CommentService commentService
    UserService userService
    PostRepository postRepository

    def setup(){
        commentService = Mock()
        userService = Mock()
        postRepository = Mock()
        commentController = new CommentController()
        commentController.commentService = commentService
        commentController.postRepository = postRepository
        commentController.userService = userService
    }

    def "test add comment"(){

        given: "a user"
        userService.findUser(1) >> Mock(User)

        and: "a post"
        postRepository.findOne(1) >> Mock(Post)

        when: "the add comment URL is triggered"
        commentController.addComment(1,1,"ceci est un commentaire")

        then: "the save is delegated to the commentService"
        1 * commentService.saveComment(_)
    }

    def "test delegation of delete comment to commentService"(){
        given: "a comment id"
        Long commentId = 1

        when: "the delete comment is triggered"
        commentController.deleteComment(commentId)

        then: "the delete is delegated to the commentService"
        1 * commentService.deleteComment(commentId)
    }

}
