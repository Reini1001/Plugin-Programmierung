package de.hs_fl.sbg.plugins.datafile_converter.converter.to

import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.Node
import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.Tree
import org.junit.Test
import java.io.File

class ConverterToJSONTest {

    @Test
    fun convertAndWriteTest() {
        val tree = Tree(Node("rootNode"))
        tree.root.addProperty("testInt", 1)
        tree.root.addProperty("testFloat", 3.141)
        tree.root.addProperty("testBool", false)
        tree.root.addProperty("testNull", null)
        tree.root.addProperty("testString", "String")

        val extraNode = Node("testNodeNoChildren")
        extraNode.addProperty("Test", "test")

        tree.root.addChild(extraNode)

        val extraNode2 = Node("testNodeNoProperties")
        val extraNode3 = Node("testNodeEmpty")

        extraNode2.addChild(extraNode3)

        tree.root.addChild(extraNode2)

        val converter = ConverterToJSON()
        converter.convertAndWrite(tree, "./src/test/kotlin/de/hs_fl/sbg/plugins/datafile_converter/converter/Test")
        assert(File("./src/test/kotlin/de/hs_fl/sbg/plugins/datafile_converter/converter/Test.json").exists())
    }
}