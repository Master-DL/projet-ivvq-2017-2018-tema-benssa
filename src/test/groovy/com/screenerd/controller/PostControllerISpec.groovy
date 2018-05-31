package com.screenerd.controller

import com.screenerd.domain.Post
import com.screenerd.domain.User
import com.screenerd.service.PostService
import com.screenerd.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import spock.lang.Specification

@Transactional
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostControllerISpec extends Specification {
    @Autowired
    TestRestTemplate restTemplate
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    def "test add a valid post" () {

        given: "when the addUser url is triggered with valid input for user"
        byte [] avatar = [1,2]
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<String,Object>()
        map.add("login","login")
        map.add("password","password")
        map.add("avatar",avatar)
        User user = restTemplate.postForObject("/api/v1/user",map,User.class)

        when: "when the addPost url is triggered with valid input for post"

        map = new LinkedMultiValueMap<String,Object>()
        map.add("idUser",user.getId())
        map.add("image",avatar)
        map.add("imageFormat","png")
        map.add("description", "description test")
        Post post = restTemplate.postForObject("/api/v1/newPost",map,Post.class)

        then: "the post is created"
        post != null
        post.id != null
        post.description == "description test"
        post.image == avatar
        post.imageFormat == "png"
    }

    def "test findAllPosts" () {
        given: "a repo with one user"
        byte [] avatar = [1,2]
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<String,Object>()
        map.add("login","login")
        map.add("password","password")
        map.add("avatar",avatar)
        User user = restTemplate.postForObject("/api/v1/user",map,User.class)

        and: "two posts this user wrote"
        map = new LinkedMultiValueMap<String,Object>()
        map.add("idUser",user.getId())
        map.add("image",avatar)
        map.add("imageFormat","png")
        map.add("description", "post1")
        Post post1 = restTemplate.postForObject("/api/v1/newPost",map,Post.class)
        map.add("description", "post2")
        Post post2 = restTemplate.postForObject("/api/v1/newPost",map,Post.class)

        when: "we request all the posts that are saved"
        Iterable<Post> posts = restTemplate.getForObject("/api/v1/getPost", Iterable.class)

        then: "we retrieve the two posts"
        posts.asCollection().description.contains(post1.description)
        posts.asCollection().description.contains(post2.description)
    }

    def "test find one post" () {
        given: "a repo with one user"
        byte [] avatar = [1,2]
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<String,Object>()
        map.add("login","login")
        map.add("password","password")
        map.add("avatar",avatar)
        User user = restTemplate.postForObject("/api/v1/user",map,User.class)

        and: "two posts this user wrote"
        map = new LinkedMultiValueMap<String,Object>()
        map.add("idUser",user.getId())
        map.add("image",avatar)
        map.add("imageFormat","png")
        map.add("description", "post1")
        Post post1 = restTemplate.postForObject("/api/v1/newPost",map,Post.class)
        map.add("description", "post2")
        Post post2 = restTemplate.postForObject("/api/v1/newPost",map,Post.class)

        when: "we request the second post"
        Post retrievedPost = restTemplate.getForObject("/api/v1/getPost/"+post2.id, Post.class)

        then: "the id of the retrieved post is correct"
        retrievedPost != null
        post2.id == retrievedPost.id
    }
}
