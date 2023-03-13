package org.ldv.sushi.apisushi

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.ldv.sushi.apisushi.repository.BoxRepository
import org.ldv.sushi.apisushi.util.fromBoxToBoxDtoJson
import org.skyscreamer.jsonassert.JSONAssert
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestPropertySource
import java.io.File


@TestPropertySource(
	locations = ["classpath:application-test.properties"])
@DataJpaTest
class ApiSushiApplicationTests @Autowired constructor(
	var boxRepository: BoxRepository
	) {

	var logger: Logger = org.slf4j.LoggerFactory.getLogger(ApiSushiApplicationTests::class.java)

	@Test
	fun contextLoads() {
		Assertions.assertTrue(true)
	}

	@Test
	fun verifSourceJsonEtApllicationJson() {
        // compare le fichier JSON d'origine avec la version produite par l'application
		val FILE_NAME_JSON = "./boxes-sushi.json"

		val boxesJsonStr: String = File(FILE_NAME_JSON).readText(Charsets.UTF_8)

		val mapper = jacksonObjectMapper()

		val boxesJsonFromDataBase: String =
			mapper.writerWithDefaultPrettyPrinter()
			  .writeValueAsString(
				  (this.boxRepository.findAll().map { fromBoxToBoxDtoJson(it) }).toList())

		JSONAssert.assertEquals(boxesJsonStr, boxesJsonFromDataBase, false)
	}
}
