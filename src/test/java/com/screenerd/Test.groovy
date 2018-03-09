package com.screenerd;
import spock.lang.*;

/**
 * Created by mathieukostiuk on 09/03/2018.
 */
class Test extends Specification {
    def "let's try this!"() {
        expect:
        Math.max(1, 2) == 3
    }
}
