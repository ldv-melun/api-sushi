package org.ldv.sushi.apisushi
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.ldv.sushi.apisushi.domain.*
import org.ldv.sushi.apisushi.json.BoxJson
import org.ldv.sushi.apisushi.repository.AlimentBoxRepository
import org.ldv.sushi.apisushi.repository.AlimentRepository
import org.ldv.sushi.apisushi.repository.BoxRepository
import org.ldv.sushi.apisushi.repository.SaveurRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.io.File

@Order(value = 1)
@Component
class   DatabaseInitializer @Autowired constructor(
    val boxRepository: BoxRepository,
    val saveurRepository: SaveurRepository,
    val alimentRepository: AlimentRepository,
    val alimentBoxRepository: AlimentBoxRepository

    ) : ApplicationRunner {

    @Throws(Exception::class)
    override fun run(args: ApplicationArguments) {
        val FILE_NAME_JSON = "boxes-sushi.json"
        databaseInitializer(FILE_NAME_JSON)
    }

    /**
     * Initialise la base de données relationnelle à partir d'un fichier JSON initial
     */
    fun databaseInitializer(fileNameJson : String) {
        val mapper = jacksonObjectMapper()
        val boxesJsonStr : String =  File(fileNameJson).readText(Charsets.UTF_8)
        val boxesJsonList: List<BoxJson> = mapper.readValue(boxesJsonStr)

        // println(boxes)

        for (boxJson in boxesJsonList) {
            var box : Box = Box()
            box.nom = boxJson.nom
            box.prix = boxJson.prix
            box.image = boxJson.image
            box.prix = boxJson.prix
            box.nbPieces = boxJson.pieces

            for (saveur in boxJson.saveurs) {
                var s: Saveur = saveurRepository.checkSaveSaveur(saveur)
                box.saveurs.add(s)
            }

            // save a new box
            boxRepository.save(box)

            for (alimentj in boxJson.aliments ) {
                // first create/save aliment if not exists
                val aliment = alimentRepository.checkSaveAliment(alimentj.nom)
                // create/save alimentBox
                alimentBoxRepository.save(AlimentBox(box, aliment, alimentj.quantite))
            }
        }
    }
}

private fun AlimentRepository.checkSaveAliment(nom: String): Aliment {
    var aliment: Aliment?  = this.findByNom(nom)
    if (aliment == null) {
        aliment = Aliment(nom)
        this.save(aliment)
    }
    return aliment
}

private fun SaveurRepository.checkSaveSaveur(nomSaveur: String) : Saveur {
    var s: Saveur? = this.findByNom(nomSaveur)
    if (s == null) {
        s = Saveur(nomSaveur)
        this.save(s)
    }
    return s
}
