package com.screenerd

import spock.lang.Specification

/**
 * Created by telly on 09/03/18.
 */

class TestGroo extends Specification{
    def "let's try this!"() {
        expect:
        Math.max(1, 2) == 3
    }
}