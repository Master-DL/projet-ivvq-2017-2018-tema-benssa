package com.screenerd.controller;

import com.screenerd.domain.User;
import com.screenerd.repository.UserRepository;
import com.screenerd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        System.out.println(id);
        userService.deleteUser(id);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        User userUpdate = userService.saveUser(user);
        return  new ResponseEntity<User>(userUpdate, HttpStatus.OK);
    }
}
