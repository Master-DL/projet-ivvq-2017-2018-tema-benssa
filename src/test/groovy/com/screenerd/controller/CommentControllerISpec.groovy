package com.screenerd.controller

import com.screenerd.domain.Comment
import com.screenerd.domain.Like
import com.screenerd.domain.Post
import com.screenerd.domain.User
import com.screenerd.repository.CommentRepository
import com.screenerd.repository.LikeRepository
import com.screenerd.repository.PostRepository
import com.screenerd.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import spock.lang.Specification

/**
 * Created by benjamin on 19/05/18.
 */
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
class CommentControllerISpec extends Specification{

    @Autowired
    TestRestTemplate restTemplate
    @Autowired
    UserRepository userRepository
    @Autowired
    PostRepository postRepository
    @Autowired
    CommentRepository commentRepository

    User user
    Post post
    Comment comment

    def setup(){
        user = new User(login: "login",password: "password",avatar: [1, 3, 6])
        userRepository.save(user)
        post = new Post(user: user,description: "Descritpion", image: [0, 0, 0, 0, 0] as byte[],  imageFormat: "png")
        postRepository.save(post)
    }

    def "test add a valid comment"(){
        when: "when the add comment URL is triggered with valid input"
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<String,Object>()
        map.add("idUser",user.id)
        map.add("idPost",post.id)
        map.add("content","ceci est un commentaire")
        comment = restTemplate.postForObject("/api/v1/comment",map,Comment.class)

        then: "the comment is created and has correct values"
        comment.id != null
        comment.content == "ceci est un commentaire"
    }

    void "test delete comment"() {
        given: "a valid saved comment "
        Comment comment = new Comment(content: "ceci est un commentaire",user: user,post: post)
        commentRepository.save(comment)

        when: "comment is deleted"
        restTemplate.delete("/api/v1/deleteComment/${comment.id}")

        then: "the comment is deleted from database"
        !commentRepository.findOne(comment.id)

    }

}
