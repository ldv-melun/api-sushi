package org.ldv.sushi.apisushi.domain

import javax.persistence.*

@Entity
class Box(
    var nom: String = "",
    var nbPieces: Int = 0,
    var image: String = "",
    var prix: Double = 0.0
) {
    @ManyToMany
    var saveurs = mutableSetOf<Saveur>()

    @OneToMany(mappedBy = "box")
    var aliments = mutableListOf<AlimentBox>()

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
