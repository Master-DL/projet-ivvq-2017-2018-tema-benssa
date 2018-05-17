package com.screenerd.service

import com.screenerd.domain.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import javax.validation.ConstraintViolationException

/**
 * Created by telly on 17/05/18.
 */
@SpringBootTest
class UserServiceISpec extends Specification{

    @Autowired
    UserService userService

    def "test save a valid user"(){
        given: "a valid user"
        User user = new User(login: "login",password: "password",avatar: [1, 3, 6])

        when: "the user is saved"
        userService.saveUser(user)

        then: "the user has an id"
        user.id != null
    }

    def "test save a non valid user"(){
        given: "a non valid user"
        User user = new User(login: "login",password: null,avatar: [1, 3, 6])

        when: "the user is saved"
        userService.saveUser(user)

        then: "A validation exception is thrown"
        thrown ConstraintViolationException

        and: "the user has still a null id"
        user.id == null
    }
}
