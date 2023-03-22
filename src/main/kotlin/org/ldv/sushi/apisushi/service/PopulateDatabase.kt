package org.ldv.sushi.apisushi.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.ldv.sushi.apisushi.domain.Aliment
import org.ldv.sushi.apisushi.domain.AlimentBox
import org.ldv.sushi.apisushi.domain.Box
import org.ldv.sushi.apisushi.domain.Saveur
import org.ldv.sushi.apisushi.dto.BoxDtoJson
import org.ldv.sushi.apisushi.repository.AlimentBoxRepository
import org.ldv.sushi.apisushi.repository.AlimentRepository
import org.ldv.sushi.apisushi.repository.BoxRepository
import org.ldv.sushi.apisushi.repository.SaveurRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.File

@Service
class PopulateDatabase @Autowired constructor(
    val boxRepository: BoxRepository,
    val saveurRepository: SaveurRepository,
    val alimentRepository: AlimentRepository,
    val alimentBoxRepository: AlimentBoxRepository
) {

    var logger: Logger = LoggerFactory.getLogger(PopulateDatabase::class.java)

    /**
     * Initialise la base de données relationnelle à partir d'un fichier JSON initial
     */
    fun databaseInitializer(fileNameJson: String) {
        if (saveurRepository.count() > 0) {
            logger.info("Database not empty. Exit of databaseInitializer (no action)")
            return
        }
//        logger.info("/static/assets/"+fileNameJson)

        val mapper = jacksonObjectMapper()
        val data = javaClass
            .getResourceAsStream("/assets/"+fileNameJson)
        val boxesJsonStr = data.bufferedReader().readText()

//        val boxesJsonStr: String = File(fileNameJson).readText(Charsets.UTF_8)
        val boxesDtoJsonList: List<BoxDtoJson> = mapper.readValue(boxesJsonStr)

        logger.info("Database empty. Initialize database")

        for (boxDtoJson in boxesDtoJsonList) {
            val box: Box = Box(
                boxDtoJson.nom,
                boxDtoJson.pieces,
                boxDtoJson.image, boxDtoJson.prix
            )

            // box has id in json file
            box.id = boxDtoJson.id

            for (saveurNom in boxDtoJson.saveurs) {
                val s: Saveur = saveurRepository.findOrCreateSaveur(saveurNom)
                box.saveurs.add(s)
            }

            // save a new box
            boxRepository.save(box)

            for (alimentBoxDtoJson in boxDtoJson.aliments) {
                // first create/save aliment if not exists
                val aliment = alimentRepository.findOrCreateAliment(alimentBoxDtoJson.nom)
                // create/save new AlimentBox
                alimentBoxRepository.save(AlimentBox(box, aliment, alimentBoxDtoJson.quantite))
            }
        }
    }
}

// Extensions

private fun AlimentRepository.findOrCreateAliment(nom: String): Aliment {
    var aliment: Aliment? = this.findByNom(nom)
    if (aliment == null) {
        aliment = Aliment(nom)
        this.save(aliment)
    }
    return aliment
}

private fun SaveurRepository.findOrCreateSaveur(nomSaveur: String): Saveur {
    var s: Saveur? = this.findByNom(nomSaveur)
    if (s == null) {
        s = Saveur(nomSaveur)
        this.save(s)
    }
    return s
}


