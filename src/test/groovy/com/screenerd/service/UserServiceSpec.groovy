package com.screenerd.service

import com.screenerd.domain.User
import com.screenerd.repository.UserRepository
import org.springframework.data.jpa.repository.JpaRepository
import spock.lang.Specification

/**
 * Created by mousa on 13/04/2018.
 */
class UserServiceSpec extends Specification {
    UserService userService
    UserRepository userRepository

    void setup() {
        userRepository = Mock()
        userService = new UserService()
        userService.userRepository = userRepository
    }

    def "check type of userRepository"() {
        expect: "userRepository is a JpaRepository"
        userRepository instanceof JpaRepository
    }


    def "test delegation of save of an user to the repository"() {
        given: "a user"
        def user = Mock(User)

        when: "the user is saved"
        userService.saveUser(user)

        then: "the save is delegated to the userRepository"
        1 * userRepository.save(user)
    }

    def "test delegation of find user to the repository" () {
        given: "a user Id"
        def id = 1

        when: "the user is saved"
        userService.findUser(id)

        then: "the find is delegated to the userRepository"
        1 * userRepository.findOne(id)
    }

    def "test delegation of delete user to the repository" () {
        given: "a user Id"
        def id = 1

        when: "the user is deleted"
        userService.deleteUser(id)

        then: "the delete is delegated to the userRepository"
        1 * userRepository.delete(id)
    }

    def "test delegation of update user to the repository" () {
        given: "a user Id"
        def id = 1
        and: "a password"
        def password = "newPassword"
        and: "the user with this Id exists"
        userRepository.findOne(1) >> Mock(User)

        when: "the user is updated"
        userService.updateUser(id,password,null)

        then: "a save is delegated to the userRepository"
        1 * userRepository.saveAndFlush(_)
    }

    def "test delegation of authentication to the repository" (){
        when: "a user is fetched by login and password"
        userService.authenticate("login","password")

        then: "the find user by login and password is delegated to the repository"
        1 * userRepository.findByLoginAndPassword(_,_)
    }
}
