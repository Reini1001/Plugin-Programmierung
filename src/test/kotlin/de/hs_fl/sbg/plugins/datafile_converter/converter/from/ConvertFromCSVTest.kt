package de.hs_fl.sbg.plugins.datafile_converter.converter.from

import org.junit.Test
import java.io.File

class ConvertFromCSVTest {
    @Test
    fun test () {
        val input = File("./src/test/kotlin/de/hs_fl/sbg/plugins/datafile_converter/converter/from/ConvertFromCsvTest.csv")
        if (!input.exists()) {
            input.createNewFile()
            input.writeText("Name,Age,Location\nJohn,25,New York\nAlice,30,Los Angeles\nBob,22,Chicago\nEva,28,San Francisco")
        }

        val tree = ConvertFromCSV().convert(input)

        println()
        tree.root.getChildren().forEach {
            it.getProperties().forEach { property ->
                print("${property.key}: ${property.value} ")
            }
            println()
        }
        println()

        assert(true)
    }
}