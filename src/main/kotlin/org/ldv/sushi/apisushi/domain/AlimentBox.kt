package org.ldv.sushi.apisushi.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class AlimentBox (@ManyToOne var box: Box,
                  @ManyToOne var aliment: Aliment,
                  var quantite: Int)
{
    @Id
    @GeneratedValue
    var id: Long? = null

}
