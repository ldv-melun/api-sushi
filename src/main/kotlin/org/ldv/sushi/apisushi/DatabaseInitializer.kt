package org.ldv.sushi.apisushi

import org.ldv.sushi.apisushi.domain.*
import org.ldv.sushi.apisushi.service.PopulateDatabase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class DatabaseInitializer @Autowired constructor(
    val populateDatabase: PopulateDatabase

) : ApplicationRunner {

    @Throws(Exception::class)
    override fun run(args: ApplicationArguments) {
        val FILE_NAME_JSON = "boxes-sushi.json"
        populateDatabase.databaseInitializer(FILE_NAME_JSON)
    }
}
