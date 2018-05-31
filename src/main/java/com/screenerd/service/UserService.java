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
       return userRepository.findOne(id);
    }

    public User updateUser(Long id,String password,byte[] avatar){
        User fetchedUser = userRepository.findOne(id);
        if(fetchedUser == null){
            throw new IllegalArgumentException("User must exist");
        }
        if(password != null){
            fetchedUser.setPassword(password);
        }
        if(avatar != null){
            fetchedUser.setAvatar(avatar);
        }
        userRepository.saveAndFlush(fetchedUser);
        return fetchedUser;
    }

    public Boolean authenticate(String login, String password) {
        User user = this.userRepository.findOneByLoginAndPassword(login,password);
        return user!=null;
    }
}
