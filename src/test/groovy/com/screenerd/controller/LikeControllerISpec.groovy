package com.screenerd.controller

import com.screenerd.domain.Like
import com.screenerd.domain.Post
import com.screenerd.domain.User
import com.screenerd.repository.LikeRepository
import com.screenerd.repository.PostRepository
import com.screenerd.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import spock.lang.Specification

import javax.transaction.Transactional

/**
 * Created by telly on 19/05/18.
 */
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
class LikeControllerISpec extends Specification{

    @Autowired
    TestRestTemplate restTemplate
    @Autowired
    UserRepository userRepository
    @Autowired
    PostRepository postRepository
    @Autowired
    LikeRepository likeRepository

    User user
    Post post
    Like like

    def setup(){
        user = new User(login: "login",password: "password",avatar: [1, 3, 6])
        userRepository.save(user)
        post = new Post(user: user,description: "Descritpion", image: [0, 0, 0, 0, 0] as byte[],  imageFormat: "png")
        postRepository.save(post)
    }

    def "test add a valid like"(){
        when: "when the add like URL is triggered with valid input"
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<String,Object>()
        map.add("userId",user.id)
        map.add("postId",post.id)
        map.add("value",1)
        like = restTemplate.postForObject("/api/v1/like",map,Like.class)

        then: "the like is created and has correct values"
        like.id != null
        like.value == 1
    }

    def cleanup(){
        likeRepository.delete(like)
        postRepository.delete(post)
        userRepository.delete(user)
    }
}
