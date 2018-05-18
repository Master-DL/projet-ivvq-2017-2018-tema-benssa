package com.screenerd.controller

import com.screenerd.service.UserService
import spock.lang.Specification

/**
 * Created by telly on 17/05/18.
 */
class UserControllerSpec extends Specification {

    UserController userController
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
}
