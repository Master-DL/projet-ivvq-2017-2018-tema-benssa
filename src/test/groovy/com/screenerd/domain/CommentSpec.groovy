package com.screenerd

import com.screenerd.domain.Comment
import com.screenerd.domain.Post
import com.screenerd.domain.User
import spock.lang.Specification
import spock.lang.Unroll

import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory


class CommentSpec extends Specification {

    Validator validator

    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Unroll
    void "test la validite d'un commentaire valide"(String unContent, User unUser, Post unPost) {

        given: "a correctly "
        Comment comment = new Comment(content: unContent, user: unUser, post: unPost)

        expect: "the comment is valid"
        validator.validate(comment).empty

        where:
        unContent        | unUser          | unPost
        "commentaire"    | new User()      | new Post()

    }

    @Unroll
    void "test l'invalidite d'un comentaire non valide"(String unContent, User unUser, Post unPost) {

        given: "un commentaire initialise de maniere non valide"
        Comment comment = new Comment(content: unContent, user: unUser, post: unPost)

        expect: "l'utilisateur est invalide"
        !validator.validate(comment).empty

        where:
        unContent        | unUser       | unPost
        null             | new User()   | new Post()
        "commentaire"    | null         | new Post()
        "commentaire"    | new User()   | null
        "c"              | new User()   | null
    }
}