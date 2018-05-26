package com.screenerd.service

import com.screenerd.domain.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import javax.validation.ConstraintViolationException


@SpringBootTest
@Transactional
class UserServiceISpec extends Specification {

    @Autowired UserService userService

    def "test save a valid user"() {
        given: "a valid user"
        User user = new User(login:  "thomas",password: "123456", avatar: [1,2,3] as byte[])

        when: "the user is saved"
        userService.saveUser(user)

        then: "the user has an id"
        user.id != null

    }

    def "test save a non valid user"() {
        given: "a non valid user"
        User thomas = new User(login: "", password: "123456",avatar: [1,2,3] as byte[])

        when: "the user is saved"
        userService.saveUser(thomas)

        then: "A validation exception is thrown"
        thrown ConstraintViolationException

        and: "the user has still a null id"
        thomas.id == null
    }

    def "test retrieve an already added user" () {
        given: "a User that has already been added"
        User user = new User(login: "thomas", password: "123456",avatar: [1,2,3] as byte[])
        user = userService.saveUser(user)

        when: "we request the user"
        User retrievedUser = userService.findUser(user.id)

        then: "the user id is correct"
        retrievedUser.id == user.id
    }

    def "test delete existing user"(){
        given: "a User that has already been added"
        User user = new User(login: "thomas", password: "123456",avatar: [1,2,3] as byte[])
        Long id = userService.saveUser(user).id

        when: "we delete the user"
        userService.deleteUser(id)

        then: "the user no longer exists"
        userService.findUser(id) == null
    }

    def "test update existing user with valid password and avatar"(){
        given: "a User that has already been added"
        User user = new User(login: "thomas", password: "123456",avatar: [1,2,3] as byte[])
        Long id = userService.saveUser(user).id
        and: "a new valid password"
        String password = "newPassword"
        and: "a new valid avatar"
        byte [] avatar = [1,2,3,4] as byte []

        when: "we update the user"
        User updatedUser = userService.updateUser(id,password,avatar)

        then: "the updated user has the correct id"
        updatedUser.id == id
        and: "the new password"
        updatedUser.password == "newPassword"
        and: "the new avatar"
        updatedUser.avatar == [1,2,3,4] as byte []
    }

    def "test update existing user with invalid password"(){
        given: "a User that has already been added"
        User user = new User(login: "thomas", password: "123456",avatar: [1,2,3] as byte[])
        Long id = userService.saveUser(user).id
        and: "a new invalid password"
        String password = "a"

        when: "we update the user"
        userService.updateUser(id,password,null)

        then: "A validation exception is thrown"
        thrown ConstraintViolationException
    }

    def "test update inexisting user"(){
        given: "an inexisting user Id"
        Long id = Long.MAX_VALUE
        and: "a new valid password"
        String password = "newPassword"
        and: "a new valid avatar"
        byte [] avatar = [1,2,3,4] as byte []

        when: "we update the user"
        userService.updateUser(id,password,avatar)

        then: "an illegal Argument exception is thrown"
        thrown IllegalArgumentException
    }
}
