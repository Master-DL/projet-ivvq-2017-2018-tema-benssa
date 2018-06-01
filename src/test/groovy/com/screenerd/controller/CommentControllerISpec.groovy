package com.screenerd.controller

import com.screenerd.domain.Comment
import com.screenerd.domain.Like
import com.screenerd.domain.Post
import com.screenerd.domain.User
import com.screenerd.repository.CommentRepository
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

/**
 * Created by benjamin on 19/05/18.
 */
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
class CommentControllerISpec extends Specification{

    @Autowired
    TestRestTemplate restTemplate
    @Autowired
    CommentRepository commentRepository
    @Autowired
    InitializationService initializationService

    def "test add a valid comment"(){
        when: "when the add comment URL is triggered with valid input"
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<String,Object>()
        map.add("idUser",initializationService.ben.id)
        map.add("idPost",initializationService.pesByThomas.id)
        map.add("content","ceci est un commentaire")
        def comment = restTemplate.postForObject("/api/v1/comment",map,Comment.class)

        then: "the comment is created and has correct values"
        comment.id != null
        comment.content == "ceci est un commentaire"
    }

    void "test delete comment"() {
        given: "a valid saved comment "
        Comment comment = new Comment(content: "ceci est un commentaire",user: initializationService.ben,
                post: initializationService.pesByThomas)
        commentRepository.save(comment)

        when: "comment is deleted"
        restTemplate.delete("/api/v1/comment/${comment.id}")

        then: "the comment is deleted from database"
        !commentRepository.findOne(comment.id)
    }

}
