package com.screenerd.domain

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

    def "la validité d'un like valide"(int value,User user,Post post){

        given:"un like correctement initialisé "
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