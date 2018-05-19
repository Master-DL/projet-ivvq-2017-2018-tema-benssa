package com.screenerd.controller

import com.screenerd.domain.User
import com.screenerd.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import spock.lang.Specification



@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerISpec extends Specification{

    @Autowired
    TestRestTemplate restTemplate
    @Autowired
    private UserRepository userRepository;

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
    void "test delete user"(Long id) {

        when: "user delete"
        restTemplate.delete("/api/v1/newUser/${id}")

        then: "it is true of the database"
        userRepository.findOne(id)

        where:
        id|_
        1 |_
    }

}
