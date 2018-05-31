package com.screenerd.controller

import com.screenerd.domain.User
import com.screenerd.service.UserService
import spock.lang.Specification

class UserControllerSpec extends Specification {

    UserController userController
    UserService userService

    def setup(){
        userService = Mock()
        userController = new UserController()
        userController.userService = userService
    }

    def "test delegation of add user to userService"(){
        when: "the add user is called"
        userController.addUser("login","password",[1,2] as byte[])

        then: "the save is delegated to the userService"
        1 * userService.saveUser(_)
    }

    def "test delegation of delete user to userService"() {
        when: "the delete user is called"
        userController.deleteUser(1)

        then: "the delete is delegated to the userService"
        1 * userService.deleteUser(1)
    }

    def "test delegation of update user to userService"() {
        when: "the update user is called"
        userController.updateUser(1,"password",[1,2] as byte[])

        then: "the update is delegated to the userService"
        1 * userService.updateUser(1,"password",[1,2] as byte[])
    }

    def "test delegation of authentication to the userService"(){
        when: "the authenticate is called"
        userController.authenticateUser("login","password")

        then: "the authentication is delegated to the userService"
        1 * userService.authenticate("login","password")
    }
}
