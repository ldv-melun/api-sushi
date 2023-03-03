package org.ldv.sushi.apisushi.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.OneToMany

@Entity
class Box {

    @Id
    @GeneratedValue
    var id: Long? = null

    var nom: String = ""
    var nbPieces: Int = 0
    var image: String = ""
    var prix: Double = 0.0

    @ManyToMany
    var saveurs = mutableSetOf<Saveur>()

    @OneToMany(mappedBy = "box")
    var aliments = mutableListOf<AlimentBox>()


}
