package com.screenerd.service;

import com.screenerd.domain.User;
import com.screenerd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by telly on 17/05/18.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user){
        userRepository.save(user);
        return user;
    }
    public void deleteUser(Long id){
        userRepository.delete(id);
    }

    public User findUser(Long id){
       return userRepository.findById(id);
    }
}
