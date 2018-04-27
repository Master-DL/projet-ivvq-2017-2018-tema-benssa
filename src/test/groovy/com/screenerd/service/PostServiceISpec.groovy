package com.screenerd.service

import com.screenerd.domain.Post
import com.screenerd.domain.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

/**
 * Created by mathieukostiuk on 27/04/2018.
 */

@ContextConfiguration
@SpringBootTest
@Transactional
class PostServiceISpec extends Specification {
    @Autowired PostService postService


    def "test save a valid post"() {
        given: "a user"
        User user = new User("test", "test", [0, 0, 0, 0, 0] as byte[]);

        and: "a valid post"
        Post post = new Post(user,[0, 0, 0, 0, 0] as byte[], "test", "test");

        when: "the post is saved"
        postService.savePost(post)

        then: "the post has an id after been saved"
        post.id != null

        and: "the user test has also an id"
        user.id != null

        and: "the user test has a post"
        user.getPosts().size() == 1
        user.getPosts().first().getDescription() == post.getDescription()

    }
}
