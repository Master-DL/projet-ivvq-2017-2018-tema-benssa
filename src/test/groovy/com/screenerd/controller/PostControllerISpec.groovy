package com.screenerd.controller

import com.screenerd.domain.Post
import com.screenerd.domain.User
import com.screenerd.repository.PostRepository
import com.screenerd.repository.UserRepository
import com.screenerd.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import spock.lang.Specification

@ContextConfiguration
@Transactional
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostControllerISpec extends Specification {
    @Autowired
    TestRestTemplate restTemplate
    @Autowired
    private UserService userService;
    @Autowired
    private PostRepository postRepository;

    def "test add a valid post" () {

        given: "when the addUser url is triggered with valid input for user"
        byte [] avatar = [1,2]
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<String,Object>()
        map.add("login","login")
        map.add("password","password")
        map.add("avatar",avatar)
        User user = restTemplate.postForObject("/api/v1/newUser",map,User.class)

        when: "when the addPost url is triggered with valid input for post"

        map = new LinkedMultiValueMap<String,Object>()
        map.add("idUser",user.getId())
        map.add("image",avatar)
        map.add("imageFormat","png")
        map.add("description", "description test")
        Post post = restTemplate.postForObject("/api/v1/newPost",map,Post.class)

        then: "the post is created"
        post != null
    }
}
