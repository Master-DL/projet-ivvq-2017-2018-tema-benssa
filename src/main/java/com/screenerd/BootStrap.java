package com.screenerd;

import com.screenerd.service.InitializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by telly on 26/05/18.
 */
@Component
public class BootStrap {

    @Autowired
    private InitializationService initializationService;
    private Logger logger = Logger.getAnonymousLogger();

    @PostConstruct
    public void init(){
        try {
            initializationService.initUsers();
            initializationService.initPosts();
            initializationService.initComments();
            initializationService.initLikes();
        } catch (Exception e){
            logger.log(Level.SEVERE,"bootstrap exception",e.getStackTrace());
        }
    }
}
