package org.ldv.sushi.apisushi.domain

import javax.persistence.*

/**
 * Reification de la classe association AlimentBox (voi doc/analyse)
 */
@Entity
@Table( uniqueConstraints = [UniqueConstraint(columnNames = ["box_id", "aliment_id"])])
class AlimentBox (@ManyToOne var box: Box,
                  @ManyToOne var aliment: Aliment,
                  var quantite: Float)
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
