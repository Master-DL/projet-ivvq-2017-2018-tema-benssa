package com.screenerd.controller

import com.screenerd.repository.UserRepository
import com.screenerd.service.UserService
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@Transactional
class UserControllerSpec extends Specification {

    UserController userController
    UserRepository userRepository
    UserService userService
    byte[] avatar = [1,2]

    def setup(){
        userService = Mock()

        userController = new UserController()
        userController.userService = userService
    }

    def "test add user"(){
        when: "when the addUser url is triggered"
        userController.addUser("login","password",avatar)

        then: "the save is delegated to the userService"
        1 * userService.saveUser(_)
    }

    def "test delete user"() {
        when: "the requte delete"
        userController.deleteUser(1);
        then: "the save is delegated to the userService"
        1 * userService.deleteUser(1)
    }
}
