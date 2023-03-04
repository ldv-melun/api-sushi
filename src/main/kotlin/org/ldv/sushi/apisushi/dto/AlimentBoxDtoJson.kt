package org.ldv.sushi.apisushi.dto

class AlimentBoxDtoJson (
    var nom: String,
    var quantite: Int) {

    override fun toString(): String {
        return "AlimentBoxDtoJson(nom='$nom', quantite=$quantite)"
    }
}
