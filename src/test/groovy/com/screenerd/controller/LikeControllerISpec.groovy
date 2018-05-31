package com.screenerd.controller

import com.screenerd.domain.Like
import com.screenerd.domain.Post
import com.screenerd.domain.User
import com.screenerd.repository.LikeRepository
import com.screenerd.repository.PostRepository
import com.screenerd.repository.UserRepository
import com.screenerd.service.InitializationService
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
    InitializationService initializationService
    @Autowired
    LikeRepository likeRepository

    def "test add a valid like"(){
        given: "a saved user"
        User sarah = initializationService.sarah
        and: "a saved post"
        Post pesByThomas = initializationService.pesByThomas

        when: "when the add like URL is triggered with valid input"
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<String,Object>()
        map.add("userId",sarah.id)
        map.add("postId",pesByThomas.id)
        map.add("value",1)
        Like like = restTemplate.postForObject("/api/v1/like",map,Like.class)

        then: "the like is created and has correct values"
        like.id != null
        like.value == 1
    }

    def "test delete a like"(){
        when: "a like is deleted"
        restTemplate.delete("/api/v1/like/${initializationService.benHatesFortnite.id}",Void.class)

        then:"the like is deleted"
        !likeRepository.findOne(initializationService.benHatesFortnite.id)
    }
}
