package com.screenerd.controller

import com.screenerd.domain.Post
import com.screenerd.domain.User
import com.screenerd.repository.PostRepository
import com.screenerd.repository.UserRepository
import com.screenerd.service.LikeService
import spock.lang.Specification

/**
 * Created by telly on 19/05/18.
 */
class LikeControllerSpec extends Specification{

    LikeController likeController
    LikeService likeService
    UserRepository userRepository
    PostRepository postRepository

    def setup(){
        likeService = Mock()
        userRepository = Mock()
        postRepository = Mock()
        likeController = new LikeController()
        likeController.likeService = likeService
        likeController.userRepository = userRepository
        likeController.postRepository = postRepository
    }

    def "test delegation add like to likeService"(){
        given: "a user with the id 1 exists"
        userRepository.findOne(1) >> Mock(User)
        and: "a post with the id 1 exists"
        postRepository.findOne(1) >> Mock(Post)

        when: "the add like is triggered"
        likeController.addLike(1,1,2)

        then: "the save is delegated to the likeService"
        1 * likeService.saveLike(_)
    }

    def "test delegation of delete like to likeService"(){
        given: "a like id"
        Long likeId = 1
        and: "a user Id"
        Long userId = 1

        when: "the delete like is triggered"
        likeController.deleteLike(likeId,userId)

        then: "the delete is delegated to the likeService"
        1 * likeService.deleteLike(_,_)
    }
}
