package de.hs_fl.sbg.plugins.datafile_converter.converter.from

import org.apache.commons.lang3.tuple.MutablePair
import org.junit.Test
import java.io.File

class ConvertFromXMLTest {

    @Test
    fun testConvertForProperties() {
        val input = File("./src/test/kotlin/de/hs_fl/sbg/plugins/datafile_converter/converter/from/testConvertForProperties.xml")
        if (!input.exists()) input.createNewFile()

        input.writeText("<Test id=\"123\" name=\"Hallo\" hasTest=\"true\" testFloat=\"123.456\"></Test>")

        val converter = ConvertFromXML()
        val output = converter.convert(input)

        assert(output.root != null)

        val node = output.root!!

        node.getProperties().forEach {
            println("${it.key} = ${it.value} | ${it.value.javaClass.simpleName}")
        }

        assert(node.getProperties().isNotEmpty())
        assert(node.getProperties().contains(MutablePair("id", (123).toShort())))
        assert(node.getProperties().contains(MutablePair("name", "Hallo")))
        assert(node.getProperties().contains(MutablePair("hasTest", true)))
        assert(node.getProperties().contains(MutablePair("testFloat", 123.456)))
    }

    @Test
    fun testConvertForChildren() {
        val input = File("./src/test/kotlin/de/hs_fl/sbg/plugins/datafile_converter/converter/from/testConvertForChildren.xml")
        if (!input.exists()) input.createNewFile()

        input.writeText("<Layer1><Layer2a><Layer3></Layer3></Layer2a><Layer2b></Layer2b></Layer1>")

        val converter = ConvertFromXML()
        val output = converter.convert(input)

        assert(output.root != null)

        val node = output.root!!

        assert(node.getChildren().any { it.name == "Layer2a" })
        assert(node.getChildren().any { it.name == "Layer2b" })

        val layer2a = node.getChildren().find { it.name == "Layer2a" }!!

        assert(layer2a.getChildren().any{ it.name == "Layer3" })
    }
}