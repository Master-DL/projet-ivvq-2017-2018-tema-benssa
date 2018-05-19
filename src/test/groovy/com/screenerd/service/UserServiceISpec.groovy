package com.screenerd.service

import com.screenerd.domain.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import javax.validation.ConstraintViolationException


@SpringBootTest
class UserServiceISpec extends Specification {
    @Autowired UserService userService

    def "test save a valid utilisateur"() {
        given: "a valid utilisateur"
        User user = new User(login: "thomas", password: "123456",avatar: "")

        when: "the utilisateur is saved"
        userService.saveUser(user);

        then: "the utilisateur has an id"
        user.id != null

    }

    def "test save a non valid utilisateur"() {
        given: "a non valid utilisateur"
        User thomas = new User(login: "", password: "123456",avatar: "")

        when: "the utilisateur is saved"
        userService.saveUser(thomas);

        then: "A validation exception is thrown"
        thrown ConstraintViolationException

        and: "the user has still a null id"
        thomas.id == null
    }
}
