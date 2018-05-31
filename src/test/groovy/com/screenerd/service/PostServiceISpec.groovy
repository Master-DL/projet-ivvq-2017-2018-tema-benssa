package com.screenerd.service

import com.screenerd.domain.Like
import com.screenerd.domain.Post
import com.screenerd.domain.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

/**
 * Created by mathieukostiuk on 27/04/2018.
 */

@SpringBootTest
@Transactional
class PostServiceISpec extends Specification {

    @Autowired
    PostService postService
    @Autowired
    InitializationService initializationService
    @Autowired
    LikeService likeService;


    def "test save a valid post"() {
        given: "a user"
        User user = initializationService.ben

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

    def "test a non valid post" () {
        given: "a non valid post"
        Post post = new Post(null,[0, 0, 0, 0, 0] as byte[], "test", "test")

        when: "the post is saved"
        postService.savePost(post)

        then: "a validation exception is thrown"
        thrown javax.validation.ConstraintViolationException

        and: "post has no ID"
        !post.getId()
    }

    def "test find posts for the first page" () {
        given: "a pageable for the first ten posts"
        Pageable pageable = new PageRequest(0,10)

        when: "requesting for all posts"
        Page<Post> page = postService.findPage(pageable)

        then: "the total number of posts is 4 same as initialization Service number of post"
        page.totalElements == 4
        and: "the result references these 4 posts "
        page.numberOfElements == 4
        and: "the result contains all posts from initialization Service"
        page.content.contains(initializationService.fortniteByThomas)
        page.content.contains(initializationService.pesByThomas)
        page.content.contains(initializationService.catBySarah)
        page.content.contains(initializationService.fifaByBen)
    }

    def "test find posts for the second page" () {
        given: "a pageable for the posts from 10 to 19"
        Pageable pageable = new PageRequest(1, 10)

        when: "requesting for all posts"
        Page<Post> page = postService.findPage(pageable)

        then: "the total number of posts is 4"
        page.totalElements == 4
        and: "the result references 0 posts"
        page.numberOfElements == 0
    }


    def "delete one post" () {
        given: "one valid User"
        User user = initializationService.ben

        and: "two posts this user wrote"
        Post post1 = new Post(user,[0, 0, 0, 0, 0] as byte[], "test1", "test1")
        Post post2 = new Post(user,[0, 0, 0, 0, 0] as byte[], "test2", "test2")

        and: "we save the 2 posts"
        post1 = postService.savePost(post1)
        post2 = postService.savePost(post2)

        when: "we delete the first post"
        postService.deletePost(post1.getId())

        and: "requesting for the post"
        Post post = postService.findPostById(post1.getId())

        then: "the result references 1 post"
        post == null

    }

    def "retrieve one post with its id" () {
        given: "one valid user"
        User user = initializationService.ben

        and: "a post this user wrote"
        Post post1 = new Post(user,[0, 0, 0, 0, 0] as byte[], "test1", "test1")

        and: "this post is saved in the repo"
        post1 = postService.savePost(post1)
        def id = post1.getId()

        when: "we request this post with its id"
        def retrivedPost = postService.findPostById(id)

        then: "the retrievedPost is not null and has the same description"
        retrivedPost != null
        retrivedPost.description == post1.description
    }

    def "retrieve posts ordered by popularity" () {
        given: "two posts"
        def post1 = initializationService.getPesByThomas()
        def post2 = initializationService.catBySarah

        and: "three likes"
        def like1 = new Like(1,initializationService.ben, post2)
        def like2 = new Like(1, initializationService.ben, post2)
        def like3 = new Like(4, initializationService.ben, post1)
        def like4 = new Like(5, initializationService.ben, post2)

        and: "we add one like with value =1"
        likeService.saveLike(like3)
        post1.addLike(like3)

        and: "we add three like with values = 1, 1, 5"
        likeService.saveLike(like1)
        likeService.saveLike(like2)
        likeService.saveLike(like4)
        post2.addLike(like1)
        post2.addLike(like2)
        post2.addLike(like4)

        when: "we retrieve the posts ordered by popularity"
        Pageable pageable = new PageRequest(0, 10)
        Page<Post> posts = postService.findPageOrderedByPopularity(pageable)

        then: "the first post is post2"
        println(post2.getPopularity())
        println(post1.getPopularity())

        posts.asList().get(0).getPopularity() == post1.getPopularity()

        and: "the second post is post1"
        posts.asList().get(1).id == post2.id
    }
}
