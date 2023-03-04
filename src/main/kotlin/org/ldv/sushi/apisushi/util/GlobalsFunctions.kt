package org.ldv.sushi.apisushi.util

import org.ldv.sushi.apisushi.domain.Box
import org.ldv.sushi.apisushi.dto.AlimentBoxDtoJson
import org.ldv.sushi.apisushi.dto.BoxDtoJson

fun fromBoxToBoxDtoJson(box: Box): BoxDtoJson {
    return BoxDtoJson(
        box.id,
        box.nbPieces,
        box.nom,
        box.image,
        box.prix,
        HashSet(box.saveurs.stream().map { it.nom }.toList()),
        box.aliments.stream().map { AlimentBoxDtoJson(it.aliment.nom, it.quantite) }.toList()
    )
}
