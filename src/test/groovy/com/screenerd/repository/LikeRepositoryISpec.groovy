package com.screenerd.repository

import com.screenerd.domain.Like
import com.screenerd.domain.Post
import com.screenerd.domain.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification


/**
 * Created by telly on 18/05/18.
 */
@SpringBootTest
@Transactional
class LikeRepositoryISpec extends Specification{

    @Autowired
    LikeRepository likeRepository
    @Autowired
    UserRepository userRepository
    @Autowired
    PostRepository postRepository

    def "test save valid like"(){
        given: "a saved user"
        User user = new User(login: "login",password: "password",avatar: [1, 3, 6])
        userRepository.save(user)
        and: "a saved post"
        Post post = new Post(user: user,description: "Descritpion", image: [0, 0, 0, 0, 0] as byte[],  imageFormat: "png", popularity: 1)
        postRepository.save(post)
        and: "a valid like"
        Like like = new Like(1,user,post)

        when: "the like is saved"
        Like savedLike = likeRepository.save(like)

        then: "the savedLike is like"
        savedLike == like

        and: "the like has an id"
        savedLike.id != null

        when: "the like is fetched"
        Like fetchedLike = likeRepository.findOne(like.id)

        then: "the like exists"
        fetchedLike != null

        and: "the like has an same Id as savedLike"
        fetchedLike.id == savedLike.id

        and: "the like contains correct information"
        fetchedLike.value == 1
        fetchedLike.user == user
        fetchedLike.post == post
    }

    def "test delete like"(){
        given: "a valid saved user"
        User user = new User(login: "login",password: "password",avatar: [1, 3, 6])
        userRepository.save(user)
        and: "a valid post"
        Post post = new Post(user: user,description: "Descritpion", image: [0, 0, 0, 0, 0] as byte[],  imageFormat: "png", popularity: 1)
        postRepository.save(post)
        and: "a saved like"
        Like like = new Like(1,user,post)
        Like saved = likeRepository.save(like)

        when: "the saved like is deleted"
        likeRepository.delete(saved.id)

        then: "the saved like no longer exists"
        !likeRepository.findOne(saved.id)
    }
}
