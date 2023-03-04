package org.ldv.sushi.apisushi.dto

import org.ldv.sushi.apisushi.domain.Box

class BoxDtoJson(
    val id: Long?,
    val pieces: Int,
    val nom: String,
    val image: String,
    val prix: Double,
    val saveurs: Set<String>,
    val aliments: List<AlimentBoxDtoJson>
) {
    override fun toString(): String {
        return "BoxDtoJson(id=$id, pieces=$pieces, nom='$nom', image='$image', prix=$prix, saveurs=$saveurs, aliments=$aliments)"
    }
}

