package com.screenerd.service

import com.screenerd.domain.Comment
import com.screenerd.domain.Post
import com.screenerd.domain.User
import com.screenerd.repository.CommentRepository
import com.screenerd.repository.PostRepository
import com.screenerd.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional;
import spock.lang.Specification

@SpringBootTest
@Transactional
class CommentRepositoryISpec extends Specification{

    @Autowired
    private CommentRepository commentRepository
    @Autowired
    private UserRepository userRepository
    @Autowired
    private PostRepository postRepository

    def "test creation of comment"(){
        given: "a user"
        User user = new User(login: "login",password: "password",avatar: [1,3,6]);
        and: "a post"
        Post post = new Post(user: user, description: "GG", image: [0, 0, 0, 0, 0] as byte[] , imageFormat: "png", popularity: 1);
        and: "a comment"
        Comment comment = new Comment(content:"a comment", user: user, post: post);

        when: "comment is saved"
        User savedUser = userRepository.save(user)
        Post savedPost = postRepository.save(post)
        Comment savedComment = commentRepository.save(comment)
        then: "the savedComment is comment"
        savedComment == comment

        and: "the comment have an Id"
        comment.id != null

        when: "comment is fetched from base"

        Comment fetchedComment = commentRepository.findOne(comment.id)
        then: "the comment exists"
        fetchedComment != null

        and: "the comment contains correct informations"
        fetchedComment.content == "a comment"
        fetchedComment.user == savedUser
        fetchedComment.post == savedPost
    }

    def "test delete comment"(){
        given: "a valid saved user"
        User user = new User(login: "login",password: "password",avatar: [1, 3, 6])
        userRepository.save(user)
        and: "a valid saved post"
        Post post = new Post(user: user,description: "Descritpion", image: [0, 0, 0, 0, 0] as byte[],  imageFormat: "png", popularity: 1)
        postRepository.save(post)
        and: "a saved comment"
        Comment comment = new Comment("ceci est un commentaire",user,post)
        Comment savedComment = commentRepository.save(comment)

        when: "the saved comment is deleted"
        commentRepository.delete(comment.id)

        then: "the saved comment no longer exists"
        !commentRepository.findOne(comment.id)
    }
}
