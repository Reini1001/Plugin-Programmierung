package de.hs_fl.sbg.plugins.datafile_converter.converter.from

import org.junit.Test
import java.io.File

class ConverterFromCSVTest {
    @Test
    fun test () {
        val input = File("./src/test/kotlin/de/hs_fl/sbg/plugins/datafile_converter/converter/from/ConvertFromCsvTest.csv")
        if (!input.exists()) {
            input.createNewFile()
            input.writeText("Name,Age,Location\nJohn,25,New York\nAlice,30,Los Angeles\nBob,22,Chicago\nEva,28,San Francisco")
        }

        val tree = ConverterFromCSV().convert(input)

        println()
        tree.root.getChildren().forEach {
            it.getProperties().forEach { property ->
                print("${property.key}: ${property.value} ")
            }
            println()
        }
        println()

        assert(tree.root.getChildren().find { entry -> entry.getProperties().values.contains("John") } != null)
        assert(tree.root.getChildren().find { entry -> entry.getProperties().values.contains("Alice") } != null)
        assert(tree.root.getChildren().find { entry -> entry.getProperties().values.contains("Eva") } != null)
    }
}