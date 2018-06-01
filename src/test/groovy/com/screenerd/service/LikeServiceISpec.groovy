package com.screenerd.service

import com.screenerd.domain.Like
import com.screenerd.domain.Post
import com.screenerd.domain.User
import com.screenerd.repository.LikeRepository
import com.screenerd.repository.PostRepository
import com.screenerd.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import javax.validation.ConstraintViolationException

/**
 * Created by telly on 19/05/18.
 */
@SpringBootTest
@Transactional
class LikeServiceISpec extends Specification{

    @Autowired
    LikeService likeService
    @Autowired
    InitializationService initializationService
    @Autowired
    LikeRepository likeRepository
    @Autowired PostRepository postRepository
    @Autowired UserRepository userRepository

   def "test save a null like"(){
        when: "a null like is saved"
        likeService.saveLike(null)

        then: "a illegal argument exception is thrown"
        thrown IllegalArgumentException
    }

    def "test save non valid like"(){
        given: "a saved user"
        User sarah = initializationService.sarah
        and: "a saved post"
        Post pesByThomas = initializationService.pesByThomas
        and: "a non valid like"
        Like like = new Like(10,sarah,pesByThomas)

        when: "the like is saved"
        likeService.saveLike(like)

        then: "A validation exception is thrown"
        thrown ConstraintViolationException

        and: "the like has still a null id"
        like.id == null
    }

    def "test save  valid like with saved user and saved post"(){
        given: "a saved user"
        User sarah = initializationService.sarah
        and: "a saved post"
        Post pesByThomas = initializationService.pesByThomas
        and: "a non valid like"
        Like like = new Like(2,sarah,pesByThomas)

        when: "the like is saved"
        likeService.saveLike(like)

        then: "the like has an id"
        like.id != null
        and: "the user contains the like"
        sarah.likes.contains(like)
        and: "the post contains the like"
        pesByThomas.likes.contains(like)
    }


    def "test delete like a saved like"(){
        given: "a saved like"
        Like like = initializationService.sarahLovesFortnite

        when: "the like with this id is deleted"
        likeService.deleteLike(like.id)

        then: "the like no longer exists"
        !likeRepository.findOne(initializationService.sarahLovesFortnite.id)
        and: "the like is removed from Sarah Like"
        !userRepository.findOne(initializationService.sarah.id).likes.contains(like)
        and: "the like is removed from Fortnite Likes"
        !postRepository.findOne(initializationService.fortniteByThomas.id).likes.contains(like)
    }

    def "test delete like an unsaved like"(){
        given: "a unsaved like Id"
        Long likeId = Long.MAX_VALUE

        when: "the like with this id is deleted"
        likeService.deleteLike(likeId)

        then: "an exception is thrown"
        thrown IllegalArgumentException
    }

}
