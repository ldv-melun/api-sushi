package org.ldv.sushi.apisushi

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.ldv.sushi.apisushi.domain.*
import org.ldv.sushi.apisushi.dto.BoxDtoJson
import org.ldv.sushi.apisushi.repository.AlimentBoxRepository
import org.ldv.sushi.apisushi.repository.AlimentRepository
import org.ldv.sushi.apisushi.repository.BoxRepository
import org.ldv.sushi.apisushi.repository.SaveurRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.io.File

@Order(value = 1)
@Component
class DatabaseInitializer @Autowired constructor(
    val boxRepository: BoxRepository,
    val saveurRepository: SaveurRepository,
    val alimentRepository: AlimentRepository,
    val alimentBoxRepository: AlimentBoxRepository

) : ApplicationRunner {

    var logger: Logger = LoggerFactory.getLogger(DatabaseInitializer::class.java)

    @Throws(Exception::class)
    override fun run(args: ApplicationArguments) {
        val FILE_NAME_JSON = "boxes-sushi.json"
        databaseInitializer(FILE_NAME_JSON)
    }

    /**
     * Initialise la base de données relationnelle à partir d'un fichier JSON initial
     */
    fun databaseInitializer(fileNameJson: String) {
        if (saveurRepository.count() > 0) {
            logger.info("Database not empty. Exit of databaseInitializer (no action)")
            return
        }
        val mapper = jacksonObjectMapper()
        val boxesJsonStr: String = File(fileNameJson).readText(Charsets.UTF_8)
        val boxesDtoJsonList: List<BoxDtoJson> = mapper.readValue(boxesJsonStr)

        logger.info("Database empty. Initialize database")

        for (boxDtoJson in boxesDtoJsonList) {
            val box: Box = Box(
                boxDtoJson.nom,
                boxDtoJson.pieces,
                boxDtoJson.image, boxDtoJson.prix
            )

            for (saveurNom in boxDtoJson.saveurs) {
                val s: Saveur = saveurRepository.checkSaveSaveur(saveurNom)
                box.saveurs.add(s)
            }

            // save a new box
            boxRepository.save(box)

            for (alimentBoxDtoJson in boxDtoJson.aliments) {
                // first create/save aliment if not exists
                val aliment = alimentRepository.checkSaveAliment(alimentBoxDtoJson.nom)
                // create/save new AlimentBox
                alimentBoxRepository.save(AlimentBox(box, aliment, alimentBoxDtoJson.quantite))
            }
        }
    }
}

// Extensions

private fun AlimentRepository.checkSaveAliment(nom: String): Aliment {
    var aliment: Aliment? = this.findByNom(nom)
    if (aliment == null) {
        aliment = Aliment(nom)
        this.save(aliment)
    }
    return aliment
}

private fun SaveurRepository.checkSaveSaveur(nomSaveur: String): Saveur {
    var s: Saveur? = this.findByNom(nomSaveur)
    if (s == null) {
        s = Saveur(nomSaveur)
        this.save(s)
    }
    return s
}
