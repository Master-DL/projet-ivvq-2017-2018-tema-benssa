package com.screenerd.service

import com.screenerd.domain.User
import com.screenerd.repository.UserRepository
import org.springframework.data.repository.PagingAndSortingRepository
import spock.lang.Specification

/**
 * Created by mousa on 13/04/2018.
 */
class UserServiceSpec extends Specification {
    UserService userService
    UserRepository userRepository

    void setup() {
        userRepository = Mock()
        userService = new UserService()
        userService.userRepository = userRepository
    }

    def "check type of utilisateurRepository"() {
        expect: "utilisateurRepository is a Spring repository"
        userRepository instanceof PagingAndSortingRepository
    }


    def "test delegation of save of an Utilisateur to the repository"() {
        given: "an utilisateur"
        def user = Mock(User)

        when: "the utilisateur is saved"
        userService.saveUser(user);

        then: "the save is delegated to the utilisateurRepository"
        1 * userRepository.save(user)
    }



}
