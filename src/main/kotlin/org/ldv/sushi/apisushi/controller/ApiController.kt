package org.ldv.sushi.apisushi.controller

import org.ldv.sushi.apisushi.json.BoxJson
import org.ldv.sushi.apisushi.json.fromBoxToBoxJson
import org.ldv.sushi.apisushi.repository.BoxRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ApiController  @Autowired constructor(private val boxRepository: BoxRepository) {

    @GetMapping("/api/boxes")
    fun allBoxes(): ResponseEntity<List<BoxJson>> {
        return ResponseEntity.ok(this.boxRepository.findAll().map { fromBoxToBoxJson(it) })
    }


}
