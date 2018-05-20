package com.screenerd.domain

import com.screenerd.domain.Post
import com.screenerd.domain.User
import spock.lang.Specification

import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory

/**
 * Created by mathieukostiuk on 30/03/2018.
 */
class PostSpec extends Specification {

    Validator validator;

    def setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    def 'test valid post' (User aUser, String aDescription, byte[] aImage, String aFormat) {

        given: "the creation of a new post"
        Post post = new Post(user: aUser, description: aDescription, image: aImage, imageFormat: aFormat)

        expect: "the post is valid"
        validator.validate(post).empty

        and: "the post has no comment and no likes"
        !post.comments
        !post.likes

        where:
        aUser      | aDescription | aImage                        |    aFormat
        new User() | "GG"         |   [0, 0, 0, 0, 0] as byte[]   |    "png"
        new User() | "ENCULE"     |   [0, 0, 0, 0, 0] as byte[]   |    "png"
        new User() | "LOL"        |   [0, 0, 0, 0, 0] as byte[]   |    "png"
        new User() | 'P'          |   [0, 0, 0, 0, 0] as byte[]   |    "png"

    }

    def 'test not valid' (User aUser, String aDescription, byte[] aImage, String aFormat) {

        given: "the creation of a new post"
        Post post = new Post(user: aUser, description: aDescription, image: aImage, imageFormat: aFormat)

        expect: "the post is not valid"
        !validator.validate(post).empty

        where:
        aUser      | aDescription | aImage                        |    aFormat
        null       | "GG"         |   [0, 0, 0, 0, 0] as byte[]   |    "png"
        new User() | "COMMENT"    |   null                        |    "png"
        new User() | "LOL"        |   [0, 0, 0, 0, 0] as byte[]   |    null
        new User() | null         |   [0, 0, 0, 0, 0] as byte[]   |    "png"

    }

    def 'test add Like' () {
        given: "a post "
        Post post = new Post(user: new User(), description:  "Descritpion", image:[0, 0, 0, 0, 0] as byte[], imageFormat:  "png")
        def nb = post.likes.size()

        when: "we add a Like"
        post.addLike(new Like());

        then: "list of like has one more like"
        post.likes.size() == nb + 1
    }

    def 'test add comment' () {
        given: "a post"
        Post post = new Post(user: new User(), description:  "Descritpion", image:[0, 0, 0, 0, 0] as byte[], imageFormat:  "png")
        def nb = post.comments.size()

        when: "we add a comment"
        post.addComment()

        then: "list of comments has one more comment"
        post.comments.size() == nb + 1
    }
}
