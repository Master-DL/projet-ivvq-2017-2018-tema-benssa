package com.screenerd.controller;

import com.screenerd.domain.User;
import com.screenerd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;



/**
 * Created by mousa on 27/04/2018.
 */

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/api/v1/user",method = RequestMethod.POST)
    public User addUser(@RequestParam(value="login") String login, @RequestParam(value="password") String password,
                        @RequestParam(value="avatar") byte[] avatar){
        User user = new User(login,password,avatar);
        return userService.saveUser(user);
    }


    @RequestMapping(value = "/api/v1/user/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }

    @RequestMapping(value = "/api/v1/user/update/{id}",method = RequestMethod.POST)
    public User updateUser(@PathVariable("id") Long id,
                            @RequestParam(value = "password",required = false) String password,
                            @RequestParam(value = "avatar",required = false) byte[] avatar){
        return userService.updateUser(id, password, avatar);
    }

    @RequestMapping(value = "/api/v1/user/authenticate", method = RequestMethod.GET)
    public Boolean authenticateUser(@RequestParam(value = "login") String login,
                                   @RequestParam(value = "password") String password) {
        return userService.authenticate(login,password);
    }
}
