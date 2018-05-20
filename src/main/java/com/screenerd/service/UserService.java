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
        User saved = userRepository.save(user);
        return saved;
    }
    public void deleteUser(Long id){
        userRepository.deleteById(id);

    }
    public User findUser(Long id){
       User user = userRepository.findById(id);
       return user;
    }
}
