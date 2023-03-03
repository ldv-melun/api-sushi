package org.ldv.sushi.apisushi.repository

import org.ldv.sushi.apisushi.domain.Aliment
import org.ldv.sushi.apisushi.domain.Saveur
import org.springframework.data.repository.CrudRepository

interface AlimentRepository : CrudRepository<Aliment, Long> {
    fun findByNom(nom: String) : Aliment?
}
