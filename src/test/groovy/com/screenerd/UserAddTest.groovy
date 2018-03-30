package com.screenerd

import com.screenerd.domain.User
import spock.lang.Specification

/**
 * Created by sara on 09/03/2018.
 */

class UserAddTest extends Specification {

    def "Tester l'ajout d'un utilisateur!"(params) {

        assert params != null
        params["email"] = 'email@email.fr'
        params["password"] = 'password'
        params["avatar"] = ''

    }

    def "la création d'un nouveau utilisateur"() {

        given "Un utilisateur avec des données correctes et sans avatar "

        def user = null

        when "On valide l'utilisateur"

        user = new User(login :"email@email.fr", password: "Custoja", avatar:"")

        then "Utilisateur est ajouté"

        user != null

    }
}