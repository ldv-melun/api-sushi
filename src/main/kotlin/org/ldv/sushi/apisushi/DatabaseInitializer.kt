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
     * Initialise la base de données relationnelle à partir d'un fichier JSON
     */
    fun databaseInitializer(fileNameJson : String) {
        val mapper = jacksonObjectMapper()
        val boxesJson : String =  File(fileNameJson).readText(Charsets.UTF_8)

        val boxesj: List<BoxJson> = mapper.readValue(boxesJson)

        // println(boxes)

        for (boxj in boxesj) {
            var box : Box = Box()
            box.nom = boxj.nom
            box.prix = boxj.prix
            box.image = boxj.image
            box.prix = boxj.prix
            box.nbPieces = boxj.pieces

            for (saveur in boxj.saveurs) {
                var s: Saveur? = saveurRepository.findByNom(saveur)
                if (s == null) {
                    s = Saveur(saveur)
                    saveurRepository.save(s)
                }
                box.saveurs.add(s)
            }

            boxRepository.save(box)

            for (alimentj in boxj.aliments ) {
                // save aliment first if not exists
                val aliment = alimentRepository.checkSaveAliment(alimentj.nom)
                // save alimentBox
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
