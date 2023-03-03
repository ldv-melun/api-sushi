package org.ldv.sushi.apisushi.json

import org.ldv.sushi.apisushi.domain.Box


class AlimentBoxJson (
    var nom: String,
    var quantite: Int) {

    override fun toString(): String {
        return "AlimentBoxJson(nom='$nom', quantite=$quantite)"
    }
}

class BoxJson {
    var id: Long? = 0
    var pieces: Int =0
    var nom: String = ""
    var image: String = ""
    var prix: Double = 0.0
    var saveurs = mutableSetOf<String>()
    var aliments = mutableListOf<AlimentBoxJson>()

    override fun toString(): String {
        return "BoxJson(id=$id, pieces=$pieces, nom='$nom', image='$image', prix=$prix, saveurs=$saveurs, aliments=$aliments)"
    }
}

fun fromBoxToBoxJson(box: Box) : BoxJson {
    var boxj : BoxJson = BoxJson()
    boxj.id = box.id
    boxj.pieces = box.nbPieces
    boxj.nom = box.nom
    boxj.prix = box.prix
    boxj.image = box.image
    boxj.saveurs = HashSet(box.saveurs.stream().map { it.nom }.toList())
    boxj.aliments = box.aliments.stream().map { AlimentBoxJson(it.aliment.nom, it.quantite) }.toList()
    return boxj
}
