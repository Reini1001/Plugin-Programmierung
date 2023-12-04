package de.hs_fl.sbg.plugins.datafile_converter.converter.to

import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.Node
import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.Tree
import org.junit.Test
import java.io.File

class ConverterToYAMLTest {

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
        //val extraNode3 = Node("testNodeEmpty")

        val extraNode4 = Node("testNode")
        extraNode4.addProperty("name", "test")
        val extraNode5 = Node("testNode")
        extraNode5.addProperty("name", "test")
        val extraNode6 = Node("testNode_1")
        extraNode6.addProperty("name", "test")
        val extraNode7 = Node("testNode")
        extraNode7.addProperty("name", "test")

        extraNode2.addChild(extraNode4)
        extraNode2.addChild(extraNode5)
        extraNode2.addChild(extraNode6)
        extraNode2.addChild(extraNode7)

        tree.root.addChild(extraNode2)

        val converter = ConverterToYAML()
        converter.convertAndWrite(tree, "./src/test/kotlin/de/hs_fl/sbg/plugins/datafile_converter/converter/Test")
        assert(File("./src/test/kotlin/de/hs_fl/sbg/plugins/datafile_converter/converter/Test.yaml").exists())
    }
}