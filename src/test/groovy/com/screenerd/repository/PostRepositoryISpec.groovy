package com.screenerd.repository

import com.screenerd.domain.Post
import com.screenerd.domain.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import javax.transaction.Transactional

@Transactional
@SpringBootTest
class PostRepositoryISpec extends Specification {

    @Autowired
    UserRepository userRepository

    @Autowired
    PostRepository postRepository

    def "save a valid post" () {
        given: "a saved user"
        User user = new User(login: "login",password: "password",avatar: [1, 3, 6])
        userRepository.save(user)

        and: "a valid post"
        Post post = new Post(user: user, description: "GG", image: [0, 0, 0, 0, 0] as byte[] , imageFormat: "png")

        when: "the post is saved"
        Post savedPost = postRepository.save(post)

        then: "the post has an id"
        savedPost.id
    }

    def "fetch a saved post"() {
        given: "a saved user"
        User user = new User(login: "login",password: "password",avatar: [1, 3, 6])
        userRepository.save(user)

        and: "a valid post"
        Post post = new Post(user: user, description: "GG", image: [0, 0, 0, 0, 0] as byte[] , imageFormat: "png")

        and: "the post is saved"
        Post savedPost = postRepository.save(post)

        when: "we fetch the post"
        Post fetchPost = postRepository.findOne(savedPost.id)

        then: "the id is correct"
        fetchPost.id == savedPost.id

        and: "the informations are corrects"
        fetchPost.description == savedPost.description
        fetchPost.imageFormat == savedPost.imageFormat
        fetchPost.image == savedPost.image
    }

    def "delete a saved post" () {
        given: "a saved user"
        User user = new User(login: "login",password: "password",avatar: [1, 3, 6])
        userRepository.save(user)

        and: "a valid post"
        Post post = new Post(user: user, description: "GG", image: [0, 0, 0, 0, 0] as byte[] , imageFormat: "png")

        and: "the post is saved"
        Post savedPost = postRepository.save(post)

        when: "we delete the post"
        postRepository.delete(savedPost.id)

        then: "we cant fetch this post anymore"
        !postRepository.findOne(savedPost.id)
    }
}
