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

    def "test save valid like"(){
        given: "a valid saved user"
        User user = new User(login: "login",password: "password",avatar: [1, 3, 6])
        and: "a valid post"
        Post post = new Post(user: user,description: "Descritpion", image: [0, 0, 0, 0, 0] as byte[],  imageFormat: "png")
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

        and: "the like contains correct information"
        fetchedLike.value == 1
        fetchedLike.user == user
        fetchedLike.post == post
    }
}
