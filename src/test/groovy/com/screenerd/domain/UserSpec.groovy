package com.screenerd.domain

import com.screenerd.domain.User
import spock.lang.Specification
import spock.lang.Unroll

import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory



/**
 * Created by sara on 09/03/2018.
 */

class UserSpec extends Specification {


    Validator validator

    def setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }



    def "test the invalidity of user"(String login, String password, byte[] avatar) {

        given: "a user initializes correctly"
        User user = new User(login: login, password: password, avatar: avatar)

        expect: "user is invalide"
        !validator.validate(user).empty

        where:
        login    | password  | avatar
        null     | "paswword"| ""
        "Benja"  | null      | ""
        ""       | "12"      | "hello"
        "Sara"   | "12"      | ""
        "Telly"  | "1223333" | null

    }



    def "test the validity of user"(String login, String password, byte[] avatar) {

        given: "a user initializes correctly"
        User user = new User(login: login, password: password, avatar: avatar)

        expect: "user is valide"
        validator.validate(user).empty

        where:
        login    | password  | avatar
        "Sara"   | "paswword"| ""
        "Mathieu"| "Jacques" | ""
        "Telly"  | "12345678"| ""

    }


}