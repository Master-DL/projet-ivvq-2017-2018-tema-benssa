package com.screenerd;

import com.screenerd.service.InitializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by telly on 26/05/18.
 */
@Component
public class BootStrap {

    @Autowired
    private InitializationService initializationService;

    @PostConstruct
    public void init(){
        try {
            initializationService.initUsers();
            initializationService.initPosts();
            initializationService.initComments();
            initializationService.initLikes();
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
