package com.screenerd.service

import com.screenerd.domain.Like
import com.screenerd.domain.Post
import com.screenerd.domain.User
import com.screenerd.repository.LikeRepository
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

   def "test save a null like"(){
        given: "a null like"
        Like like = null

        when: "the null like is saved"
        likeService.saveLike(like)

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

    def "test save a valid like with unsaved used and saved post"(){
        given: "a unsaved user"
        User user = new User(login: "login",password: "password",avatar: [1, 3, 6] as byte[])
        and: "a saved post"
        Post pesByThomas = initializationService.pesByThomas
        and: "a valid like"
        Like like = new Like(1,user,pesByThomas)

        when: "the like is saved"
        likeService.saveLike(like)

        then: "A validation exception is thrown"
        thrown IllegalArgumentException

        and: "the like has still a null id"
        like.id == null
    }

    def "test save a valid like with saved used and unsaved post"(){
        given: "a saved user"
        User sarah = initializationService.sarah
        and: "a unsaved post"
        Post post = new Post(sarah,[1, 3, 6] as byte[],"png","un post")
        and: "a valid like"
        Like like = new Like(1,sarah,post)

        when: "the like is saved"
        likeService.saveLike(like)

        then: "an exception is thrown"
        thrown IllegalArgumentException

        and: "the like has still a null id"
        like.id == null
    }

    def "test delete like with the right user"(){
        given: "a saved like Id"
        Long likeId = initializationService.sarahLovesFortnite.id
        and: "the id of the owner"
        Long userId = initializationService.sarah.id

        when: "the like with this id is deleted by the owner"
        likeService.deleteLike(likeId,userId)

        then: "the like is deleted"
        !likeRepository.findOne(initializationService.sarahLovesFortnite.id)
    }

    def "test delete like with the wrong user"(){
        given: "a saved like Id"
        Long likeId = initializationService.sarahLovesFortnite.id
        and: "the id of other user"
        Long userId = initializationService.ben.id

        when: "the like with this id is deleted by the owner"
        likeService.deleteLike(likeId,userId)

        then: "an exception is thrown"
        thrown IllegalArgumentException
        and: "the like still exists"
        likeRepository.findOne(initializationService.sarahLovesFortnite.id)
    }

    def "test delete not saved like"(){
        given: "a unsaved like Id"
        Long likeId = Long.MAX_VALUE

        when: "the like with this id is deleted"
        likeService.deleteLike(likeId,null)

        then: "an exception is thrown"
        thrown IllegalArgumentException
    }

}
