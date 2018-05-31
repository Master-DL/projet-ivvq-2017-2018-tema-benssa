package com.screenerd.controller

import com.screenerd.PageHelper
import com.screenerd.domain.Post
import com.screenerd.domain.User
import com.screenerd.repository.PostRepository
import com.screenerd.service.InitializationService
import com.screenerd.service.PostService
import com.screenerd.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import spock.lang.Specification

import javax.transaction.Transactional

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostControllerISpec extends Specification {

    @Autowired
    TestRestTemplate restTemplate
    @Autowired
    private InitializationService initializationService
    @Autowired
    private PostRepository postRepository


    def "test add a valid post" () {
        given: "a saved user"
        User ben = initializationService.ben

        when: "when the addPost url is triggered with valid input for post"
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<String,Object>()
        map.add("idUser",ben.getId())
        map.add("image",[1,2] as byte[])
        map.add("imageFormat","png")
        map.add("description", "description test")
        Post post = restTemplate.postForObject("/api/v1/post",map,Post.class)

        then: "the post is created"
        post.id != null
        postRepository.delete(post.id)//we delete it because testrestTemplate does not rollback transaction
    }

    def "test delete post"(){
        given: "a saved post"
        Post post = new Post(initializationService.ben,[1,2,3] as byte[],"png","une image")
        Post saved = postRepository.save(post)

        when: "the post is deleted"
        restTemplate.delete("/api/v1/post/${saved.id}")

        then: "the post no longer exists"
        !postRepository.findOne(saved.id)
        and: "the post is removed from ben posts"
        !initializationService.ben.posts.contains(saved)
    }

    def "test find first ten Posts" () {
        when: "we request the first ten posts"
        MultiValueMap<String, Integer> map = new LinkedMultiValueMap<String, Integer>()
        map.add("page", 0)
        map.add("size", 10)
        PageHelper page = restTemplate.getForObject("/api/v1/post", PageHelper.class, map)

        then: "the result references these 4 posts "
        page.numberOfElements == 4
        page.content.contains(initializationService.pesByThomas)
        page.content.contains(initializationService.fortniteByThomas)
        page.content.contains(initializationService.catBySarah)
        page.content.contains(initializationService.fifaByBen)
    }

    def "test find  posts from 10 to 19" () {
        when: "we request the posts from 10 to 19"
        MultiValueMap<String, Integer> map = new LinkedMultiValueMap<String, Integer>()
        map.add("page", 1)
        map.add("size", 10)
        PageHelper page = restTemplate.getForObject("/api/v1/post", PageHelper.class, map)

        then: "the total number of posts is 0"
        page.totalElements == 0
    }

}
