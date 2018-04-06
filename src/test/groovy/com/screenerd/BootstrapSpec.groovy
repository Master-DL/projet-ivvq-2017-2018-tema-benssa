package com.screenerd

import org.springframework.boot.test.mock.mockito.MockBean
import spock.lang.Specification

/**
 * Created by mathieukostiuk on 30/03/2018.
 */

class BootstrapSpec extends Specification {

    //private Bootstrap bootstrap;

    //@MockBean
    //private InitializationService initializationService;

/*    def setupSpec() {
      //  bootstrap = new Bootstrap(initializationService);
    }

    def 'testInitMethodInvokeInitializationService' (){
        given:
        bootstrap != null
        when:
        bootstrap.init()
        then:
        verify(initializationService).initProjects();
    }

    def 'testIniBootstrapMethodCatchRuntimeExceptionComingFromInitProjects' () {
        given:
        willThrow(RuntimeException.class).given(initializationService).initProjects()
        when:
        bootstrap.init()
        then:
        notThrown(Exception.class)
    }
*/
}
