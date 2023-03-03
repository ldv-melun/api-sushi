package org.ldv.sushi.apisushi.repository

import org.ldv.sushi.apisushi.domain.Saveur
import org.springframework.data.repository.CrudRepository

interface SaveurRepository : CrudRepository<Saveur, Long> {
    fun findByNom(nom: String) : Saveur?

}
