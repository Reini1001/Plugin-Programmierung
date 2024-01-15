package de.hs_fl.sbg.plugins.datafile_converter.converter.from

import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.Node
import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.Tree
import org.junit.Test
import java.io.File

class ConverterFromJSONTest {

    @Test
    fun test () {
        val input = File("./src/test/kotlin/de/hs_fl/sbg/plugins/datafile_converter/converter/from/ConvertFromJSONTest.json")
        if (!input.exists()) {
            input.createNewFile()
            input.writeText("{\"stringKey\": \"StringValue\", \"numberKey\": 1234, \"booleanKey\": true, \"nullKey\": null, \"arrayKey\": [1, \"string\", null, true, {}], \"objectKey\": { \"nestedString\": \"Nested\", \"nestedNumber\": 5678, \"nestedBoolean\": false, \"nestedNull\": null } }")
        }

        val tree = ConverterFromJSON().convert(input)

        printTree(tree)
        assert(true)
    }

    private fun printTree(tree: Tree) {
        println(printTree(tree.root, ""))
    }

    private fun printTree(node: Node, indent: String) : String{
        var out = "$indent${node.name}"

        node.getProperties().forEach {
            out += " -${it.key}: ${it.value}"
        }

        out += "\n"

        node.getChildren().forEach {
            out += printTree(it, if (indent == "") "|--" else "   $indent")
        }
        return out
    }
}