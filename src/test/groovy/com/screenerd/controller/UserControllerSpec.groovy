package com.screenerd.controller

import com.screenerd.repository.UserRepository
import com.screenerd.service.UserService
import spock.lang.Specification

/**
 * Created by telly on 17/05/18.
 */
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
        when: "une requête de suppression est déclenchée"
        userController.deleteUser(1);
        then: "l'action est déléguée au repository"
        1 * userService.deleteUser(1)
    }
}
