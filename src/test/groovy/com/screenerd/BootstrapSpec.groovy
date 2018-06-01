package com.screenerd

import com.screenerd.service.InitializationService
import spock.lang.Specification

/**
 * Created by mathieukostiuk on 30/03/2018.
 */

class BootstrapSpec extends Specification {

    def "test bootstrap init method call initializationService"(){
        given: "a bootstrap with initializationService"
        BootStrap bootStrap = new BootStrap()
        bootStrap.initializationService = Mock(InitializationService)

        when: "the init method is triggered"
        bootStrap.init()

        then: "the init methods of InitializationService are triggered"
        1 * bootStrap.initializationService.initUsers()
        1 * bootStrap.initializationService.initPosts()
        1 * bootStrap.initializationService.initComments()
        1 * bootStrap.initializationService.initLikes()
    }

    def "test bootstrap init method catch Runtime Excecption coming from initializationService initUser method"(){
        given: "a bootstrap with initializationService that throw Runtime Exception on initUsers"
        BootStrap bootStrap = new BootStrap()
        bootStrap.initializationService = Mock(InitializationService){
            initUsers() >> {throw new RuntimeException()}
        }

        when: "the init method is triggered"
        bootStrap.init()

        then: "no exception is thrown"
        noExceptionThrown()
    }
}
