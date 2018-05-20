package com.screenerd.controller

import com.screenerd.domain.User
import com.screenerd.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.transaction.annotation.Transactional
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
        User user = restTemplate.postForObject("/api/v1/user",map,User.class)

        then: "the user is created"
        user.id != null
        user.login == "login"
        user.password == "password"
        user.avatar == avatar
    }

    void "test delete user"() {
        given: "a valid saved user "
        User user = new User(login: "login",password: "password",avatar: [1, 3, 6])
        userRepository.save(user)

        when: "user is deleted"
        restTemplate.delete("/api/v1/user/${user.id}")

        then: "the user is deleted from database"
        !userRepository.findOne(user.id)

    }

}
