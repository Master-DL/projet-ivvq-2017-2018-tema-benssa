package com.screenerd.controller

import com.screenerd.domain.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import spock.lang.Specification

import javax.validation.ConstraintViolationException

/**
 * Created by telly on 17/05/18.
 */
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerISpec extends Specification{

    @Autowired
    TestRestTemplate restTemplate

    def "test add valid user"(){
        when: "when the addUser url is triggered with valid input for user"
        byte [] avatar = [1,2]
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<String,Object>()
        map.add("login","login")
        map.add("password","password")
        map.add("avatar",avatar)
        User user = restTemplate.postForObject("/api/v1/newUser",map,User.class)

        then: "the user is created"
        user.id != null
        user.login == "login"
        user.password == "password"
        user.avatar == avatar
    }

}
