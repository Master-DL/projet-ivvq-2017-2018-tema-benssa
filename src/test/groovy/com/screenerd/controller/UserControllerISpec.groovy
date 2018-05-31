package com.screenerd.controller

import com.screenerd.domain.User
import com.screenerd.repository.UserRepository
import com.screenerd.service.InitializationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import spock.lang.Specification



@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerISpec extends Specification{

    @Autowired
    TestRestTemplate restTemplate
    @Autowired
    InitializationService initializationService
    @Autowired
    UserRepository userRepository


    def "test add valid user"(){
        when: "when the addUser url is triggered with valid input for user"
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<String,Object>()
        map.add("login","login")
        map.add("password","password")
        map.add("avatar",[1,2] as byte[])
        User user = restTemplate.postForObject("/api/v1/user",map,User.class)

        then: "the user is created"
        user.id != null
        user.login == "login"
        user.password == "password"
        user.avatar == [1,2] as byte[]
    }

    def "test delete user"() {
        given: "a valid saved user"
        User user = new User("todelete","password",[1,2,3] as byte[])
        User saved = userRepository.save(user)

        when: "user is deleted"
        restTemplate.delete("/api/v1/user/${saved.id}")

        then: "the user is deleted from database"
        !userRepository.findOne(saved.id)
    }


    def "test update password of user"(){
        given: "a valid saved user "
        User mark = initializationService.mark

        when: "user is updated with valid password"
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<String,String>()
        map.add("password","newPassword")
        User updatedUser = restTemplate.postForObject("/api/v1/user/update/${mark.id}",map,User.class)

        then: "the updated user has the same id"
        updatedUser.id == mark.id
        and: "the updated user has the new password"
        updatedUser.password == "newPassword"
    }

    def "test update avatar of user"(){
        given: "a valid saved user "
        User mark = initializationService.mark

        when: "user is updated with valid avatar"
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<String,byte[]>()
        map.add("avatar",[1,0,3] as byte[])
        User updatedUser = restTemplate.postForObject("/api/v1/user/update/${mark.id}",map,User.class)

        then: "the updated user has the same id"
        updatedUser.id == mark.id
        and: "the updated user has the new avatar"
        updatedUser.avatar == [1,0,3] as byte[]
    }

    def "test update password and avatar of user"(){
        given: "a valid saved user "
        User mark = initializationService.mark

        when: "user is updated with valid avatar"
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<String,Object>()
        map.add("password","newPassword")
        map.add("avatar",[1,0,3] as byte[])
        User updatedUser = restTemplate.postForObject("/api/v1/user/update/${mark.id}",map,User.class)

        then: "the updated user has the same id"
        updatedUser.id == mark.id
        and: "the updated user has the new password"
        updatedUser.password == "newPassword"
        and: "the updated user has the new avatar"
        updatedUser.avatar == [1,0,3] as byte[]
    }

    def "test authentication of saved user"(){
        given: "a login of existing user"
        String login = initializationService.thomas.login
        and: "a password of this user"
        String password = initializationService.thomas.password

        when: "authentication is done with this login and password"
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<String,Object>()
        map.add("login",login)
        map.add("password",password)
        String res = restTemplate.getForObject("/api/v1/user/authenticate",String.class,map)

        then: "authentication succed"
        res == "True"
    }
}
