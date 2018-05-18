package com.screenerd.service

import com.screenerd.domain.User
import com.screenerd.repository.UserRepository
import org.springframework.data.repository.CrudRepository
import spock.lang.Specification

/**
 * Created by telly on 17/05/18.
 */
class UserServiceSpec extends  Specification{

    UserService userService
    UserRepository userRepository

    def setup(){
        userRepository = Mock();
        userService = new UserService();
        userService.userRepository = userRepository
    }

    def "check type of userRepository"(){
        expect: "userRepository is a CrudRepository"
        userRepository instanceof CrudRepository
    }

    def "test delegation of save of an user to the userRepository"(){
        given: "a user"
        User user = Mock(User)

        when: "the user is saved"
        userService.saveUser(user)

        then: "the save is delegated to the userRepository"
        1 * userRepository.save(user)

    }
}
