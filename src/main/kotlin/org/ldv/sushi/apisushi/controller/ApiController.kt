package org.ldv.sushi.apisushi.controller

import org.ldv.sushi.apisushi.dto.BoxDtoJson
import org.ldv.sushi.apisushi.repository.BoxRepository
import org.ldv.sushi.apisushi.util.fromBoxToBoxDtoJson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ApiController  @Autowired constructor(private val boxRepository: BoxRepository) {

    @GetMapping("/api/boxes")
    fun allBoxes(): ResponseEntity<List<BoxDtoJson>> {
        return ResponseEntity.ok(this.boxRepository.findAll().map { fromBoxToBoxDtoJson(it) })
    }

}
