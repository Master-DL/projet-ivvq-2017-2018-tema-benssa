package com.screenerd.repository

import com.screenerd.domain.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification


/**
 * Created by telly on 17/05/18.
 */
@SpringBootTest
@Transactional
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

    def "test delete user"(){
        given: "a saved user"
        User user = new User(login: "login",password: "password",avatar: [1,3,6])
        userRepository.save(user)
        Long id = user.id

        when: "the user is deleted"
        userRepository.delete(id)

        then: "the user no longer exists"
        !userRepository.findOne(id)
    }

    def "test update user"(){
        given: "a saved user"
        User user = new User(login: "login",password: "password",avatar: [1,3,6])
        userRepository.save(user)

        when: "the user is updated"
        user.setPassword("newPassword")
        userRepository.saveAndFlush(user)
        and: "the user is fetched"
        User fetcheUser = userRepository.findOne(user.id)

        then: "the user contains the new password"
        fetcheUser.password == "newPassword"

    }

}
