package com.screenerd.service

import com.screenerd.domain.Like
import com.screenerd.domain.Post
import com.screenerd.domain.User
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
    UserRepository userRepository
    @Autowired
    PostRepository postRepository
    @Autowired
    PostService postService

   def "test save a null like"(){
        given: "a null like"
        Like like = null

        when: "the null like is saved"
        likeService.saveLike(like)

        then: "a illegal argument exception is thrown"
        thrown IllegalArgumentException
    }

    def "test save non valid like"(){
        given: "a non valid like"
        Like like = new Like(2,null,null);

        when: "the like is saved"
        likeService.saveLike(like)

        then: "A validation exception is thrown"
        thrown ConstraintViolationException

        and: "the like has still a null id"
        like.id == null
    }

    def "test save a valid like with unsaved used and unsaved post"(){
        given: "a valid user"
        User user = new User(login: "login",password: "password",avatar: [1, 3, 6])
        and: "a valid post"
        Post post = new Post(user: user,description: "Descritpion", image: [0, 0, 0, 0, 0] as byte[],  imageFormat: "png")
        and: "a valid like"
        Like like = new Like(1,user,post)

        when: "the like is saved"
        likeService.saveLike(like)

        then: "the like has an id"
        like.id != null
        and: "the user has an id"
        user.id != null
        and: "the post has an id"
        post.id != null
        and: "the user contains the like"
        user.likes.contains(like)
        and: "the post contains the like"
        post.likes.contains(like)
    }

    def "test save a valid like with saved used and saved post"(){
        given: "a valid and saved user "
        User user = new User(login: "login",password: "password",avatar: [1, 3, 6])
        userRepository.save(user)
        and: "a valid and saved post"
        Post post = new Post(user: user,description: "Descritpion", image: [0, 0, 0, 0, 0] as byte[],  imageFormat: "png")
        postRepository.save(post)
        and: "a valid like"
        Like like = new Like(1,user,post)

        when: "the like is saved"
        likeService.saveLike(like)

        then: "the like has an id"
        like.id != null
        and: "the user has an id"
        user.id != null
        and: "the post has an id"
        post.id != null
        and: "the user contains the like"
        user.likes.contains(like)
        and: "the post contains the like"
        post.likes.contains(like)
    }

}
