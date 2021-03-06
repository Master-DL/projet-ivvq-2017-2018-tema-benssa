package com.screenerd.controller

import com.screenerd.domain.User
import com.screenerd.repository.PostRepository
import com.screenerd.service.PostService
import com.screenerd.service.UserService
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification
import org.springframework.data.domain.Pageable;


@Transactional
class PostControllerSpec extends Specification {

    PostService postService
    PostController postController
    PostRepository postRepository
    UserService userService
    User user

    def setup() {
        postService = Mock()
        userService = Mock(UserService) {
            findUser(1) >> Mock(User)
        }
        postRepository = Mock()
        user = Mock()
        postController = new PostController()
        postController.postService = postService
        postController.userService = userService
    }

    def "test add post" () {
        when: "the addPost Url is triggered"
        postController.addPost(1,[0, 0, 0, 0, 0] as byte[], "test", "test")

        then: "the save is delegated to the PostService"
        1 * postService.savePost(_)
    }

    def "test delete post" () {
        when: "the request delete post is sent"
        postController.deletePost(1)

        then: "the deletion is delegated to the post service"
        1 * postService.deletePost(_)
    }

    def "test findOnePost" () {
        when: "the request findOnePost is sent"
        postController.findPostById(1)

        then: "the erquest is delegated to the post service"
        1 * postService.findPostById(_)
    }

    def "test findPage" () {
        when: "the request findAllPosts is sent"
        postController.findPosts(Mock(Pageable))

        then: "the request is delegated by the post service"
        1 * postService.findPage(_)
    }
}
