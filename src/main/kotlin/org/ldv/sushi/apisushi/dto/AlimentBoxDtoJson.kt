package org.ldv.sushi.apisushi.dto

class AlimentBoxDtoJson (
    var nom: String,
    var quantite: Double) {

    override fun toString(): String {
        return "AlimentBoxDtoJson(nom='$nom', quantite=$quantite)"
    }
}
