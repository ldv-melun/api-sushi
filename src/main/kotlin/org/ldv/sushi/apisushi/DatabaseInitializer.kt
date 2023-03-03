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
class DatabaseInitializer @Autowired constructor(
    val boxRepository: BoxRepository,
    val saveurRepository: SaveurRepository,
    val alimentRepository: AlimentRepository,
    val alimentBoxRepository: AlimentBoxRepository

    ) : ApplicationRunner {

    @Throws(Exception::class)
    override fun run(args: ApplicationArguments) {
        databaseInitializer()
    }

    fun databaseInitializer() {
        val mapper = jacksonObjectMapper()
        val FILE_NAME = "boxes-sushi.json"
        val json : String =  File(FILE_NAME).readText(Charsets.UTF_8)

        val boxesj: List<BoxJson> = mapper.readValue(json)

        // println(boxes)

        for (boxj in boxesj) {
            var b : Box = Box()
            b.nom = boxj.nom
            b.prix = boxj.prix
            b.image = boxj.image
            b.prix = boxj.prix
            b.nbPieces = boxj.pieces

            for (saveur in boxj.saveurs) {
                var s: Saveur? = saveurRepository.findByNom(saveur)
                if (s == null) {
                    s = Saveur(saveur)
                    saveurRepository.save(s)
                }
                b.saveurs.add(s)
            }
            boxRepository.save(b)
            for (alimentj in boxj.aliments ) {
                val a = alimentRepository.checkSaveAlimentBox(alimentj.nom)
                alimentBoxRepository.save(AlimentBox(b, a, alimentj.quantite))
            }
        }
    }
}

private fun AlimentRepository.checkSaveAlimentBox(nom: String): Aliment {
    var a: Aliment?  = this.findByNom(nom)
    if (a == null) {
        a = Aliment(nom)
        this.save(a)
    }
    return a
}


