package com.screenerd.controller;

import com.screenerd.domain.User;
import com.screenerd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mousa on 27/04/2018.
 */

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/api/v1/newUser",method = RequestMethod.POST)
    public User addUser(@RequestParam(value="login") String login, @RequestParam(value="password") String password,
                        @RequestParam(value="avatar") byte[] avatar){
        User user = new User(login,password,avatar);
        return userService.saveUser(user);
    }
}
