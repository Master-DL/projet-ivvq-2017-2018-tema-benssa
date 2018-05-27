package com.screenerd.controller

import com.screenerd.domain.User
import com.screenerd.service.PostService
import com.screenerd.service.UserService
import org.springframework.data.domain.Pageable
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@Transactional
class PostControllerSpec extends Specification {

    PostService postService
    PostController postController
    UserService userService
    User user

    def setup() {
        postService = Mock()
        userService = Mock(UserService)
        user = Mock()
        postController = new PostController()
        postController.postService = postService
        postController.userService = userService
    }

    def "test delegation of add post to Post Service" () {
        given: "a saved user with Id 1"
        userService.findUser(1) >> Mock(User)

        when: "the addPost  is triggered"
        postController.addPost(1,[0, 0, 0, 0, 0] as byte[], "png", "test")

        then: "the save is delegated to the PostService"
        1 * postService.savePost(_)
    }

    def "test delegation of delete post to Post Service" () {
        when: "the request delete post is sent"
        postController.deletePost(1)

        then: "the deletion is delegated to the post service"
        1 * postService.deletePost(_)
    }


    def "test delegation of findPosts to Post Service" () {
        when: "the request findAllPosts is sent"
        postController.findPosts(Mock(Pageable))

        then: "the request is delegated to the post service"
        1 * postService.findPosts(_)
    }
}
