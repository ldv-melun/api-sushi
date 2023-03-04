package org.ldv.sushi.apisushi.domain

import javax.persistence.*

/**
 * Reification de la classe association AlimentBox (voi doc/analyse)
 */
@Entity
@Table( uniqueConstraints = [UniqueConstraint(columnNames = ["box_id", "aliment_id"])])
class AlimentBox (@ManyToOne var box: Box,
                  @ManyToOne var aliment: Aliment,
                  var quantite: Int)
{
    @Id
    @GeneratedValue
    var id: Long? = null

}
