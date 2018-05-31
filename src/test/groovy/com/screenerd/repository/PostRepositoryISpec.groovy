package com.screenerd.repository

import com.screenerd.domain.Like
import com.screenerd.domain.Post
import com.screenerd.domain.User
import com.screenerd.service.InitializationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import spock.lang.Specification

import javax.transaction.Transactional

@Transactional
@SpringBootTest
class PostRepositoryISpec extends Specification {

    @Autowired
    UserRepository userRepository

    @Autowired
    PostRepository postRepository

    @Autowired
    InitializationService initializationService

    @Autowired
    LikeRepository likeRepository;

    def "save a valid post" () {
        given: "a saved user"
        User user = new User(login: "login",password: "password",avatar: [1, 3, 6])
        userRepository.save(user)

        and: "a valid post"
        Post post = new Post(user: user, description: "GG", image: [0, 0, 0, 0, 0] as byte[] , imageFormat: "png", popularity: 1)

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
        Post post = new Post(user: user, description: "GG", image: [0, 0, 0, 0, 0] as byte[] , imageFormat: "png", popularity: 1)

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
        Post post = new Post(user: user, description: "GG", image: [0, 0, 0, 0, 0] as byte[] , imageFormat: "png", popularity: 1)

        and: "the post is saved"
        Post savedPost = postRepository.save(post)

        when: "we delete the post"
        postRepository.delete(savedPost.id)

        then: "we cant fetch this post anymore"
        !postRepository.findOne(savedPost.id)
    }

    def "retrieve posts ordered by popularity" () {
        given: "two posts"
        def post1 = initializationService.pesByThomas
        def post2 = initializationService.catBySarah

        and: "three likes"
        def like1 = new Like(1,initializationService.ben, post2)
        def like2 = new Like(1, initializationService.ben, post2)
        def like3 = new Like(4, initializationService.ben, post1)
        def like4 = new Like(5, initializationService.ben, post2)
        likeRepository.save(like1)
        likeRepository.save(like2)
        likeRepository.save(like3)
        likeRepository.save(like4)

        and: "we add one like with value =1"
        post1.addLike(like3)
        postRepository.save(post1)

        and: "we add three like with values = 1, 1, 4"
        post2.addLike(like1)
        post2.addLike(like2)
        post2.addLike(like4)
        postRepository.save(post2)

        when: "we retrieve the posts ordered by popularity"
        Pageable pageable = new PageRequest(0, 10)
        Page<Post> posts = postRepository.findAllByOrderByPopularityDesc(pageable)

        then: "the first post is post2"
        println(post2.getPopularity())
        println(post1.getPopularity())

        posts.asList().get(0).getPopularity() == post1.getPopularity()

        and: "the second post is post1"
        posts.asList().get(1).id == post2.id
    }

}
