package com.screenerd

import com.screenerd.domain.Like
import com.screenerd.domain.Post
import com.screenerd.domain.User
import spock.lang.Specification
import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory
/**
 * Created by telly on 06/04/18.
 */
class LikeSpec extends Specification{

    Validator validator

    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory()
        validator = factory.getValidator()
    }

    def "test la validite d'un like d'un valide"(int value,User user,Post post){

        given:"un Like correctement initialise"
        Like like = new Like(value,user,post)

        expect:"le like est valide"
        validator.validate(like).empty

        where:
        value |user       | post
            1 |new User() |new Post()
            2 |new User() |new Post()
            3 |new User() |new Post()
            4 |new User() |new Post()
            5 |new User() |new Post()
    }

    def "test l'invalidite d'un like invalide"(int value,User user,Post post){

        given:"un Like initialisé incorrectement"
        Like like = new Like(value,user,post)

        expect:"le like est valide"
        !validator.validate(like).empty

        where:
        value |user       | post
            0 |new User() |new Post()
            6 |new User() |new Post()
            2 |null       |new Post()
            4 |new User() |null
            10|null       |null
    }
}