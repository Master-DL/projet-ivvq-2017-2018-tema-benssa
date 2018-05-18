package com.screenerd.repository

import com.screenerd.domain.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification


/**
 * Created by telly on 17/05/18.
 */
@SpringBootTest
class UserRepositoryISpec extends Specification{

    @Autowired
    private UserRepository userRepository

    def "test creation of user"(){
        given: "a user"
        User user = new User(login: "login",password: "password",avatar: [1,3,6])

        when: "user is saved"
        User savedUser = userRepository.save(user)

        then: "the savedUser is user"
        savedUser == user

        and: "the user have an Id"
        user.id != null

        when: "user is fetched from base"
        User fetchedUser = userRepository.findOne(user.id)

        then: "the user exists"
        fetchedUser != null

        and: "the user contains correct informations"
        fetchedUser.login == "login"
        fetchedUser.password == "password"
        fetchedUser.avatar == [1,3,6]
    }
}
