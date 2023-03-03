package org.ldv.sushi.apisushi.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Aliment(val nom: String ) {

    @Id
    @GeneratedValue
    var id: Long? = null

}
