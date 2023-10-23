package de.hs_fl.sbg.plugins.datafile_converter.converter.to

import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.Node
import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.Tree
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonObjectBuilder
import java.io.BufferedWriter
import java.io.File

class ConverterToXML : IConvertTo {

    /**
     * Converts the [Tree] to a set of Strings in a [BufferedWriter] and writes it in a XML-file at given path
     * @param[tree] The [Tree] containing the data
     * @param[pathFileName] The Path of the file, including the name, excluding the file extension
     */
    override fun convertAndWrite(tree: Tree, pathFileName: String) {
        val outputFile = File("$pathFileName.xml")

        if (!outputFile.exists()) outputFile.createNewFile()
        val writer = outputFile.bufferedWriter()
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
        writer.newLine()

        nodeToXML(tree.root, writer)

        writer.close()
    }

    /**
     * Recursively converts the given [Node] and all of its children to JSON
     * @param[curNode] The [Node] that will be converted
     * @param[writer] The [BufferedWriter] that is writing the [File].
     */
    private fun nodeToXML(curNode: Node, writer: BufferedWriter) {
        val properties = curNode.getProperties()
        val isPropertiesEmpty = properties.isEmpty()
        val children = curNode.getChildren()
        val isChildrenEmpty = children.isEmpty()
        val name = curNode.name

        if (isChildrenEmpty && isPropertiesEmpty) {
            writer.appendLine("<$name/>")
        } else if(!isChildrenEmpty) {
            if (isPropertiesEmpty)
                writer.appendLine("<$name>")
            else {
                writer.append("<$name")
                for (pair in properties) {
                    writer.append(" ${pair.key}=\"${pair.value}\"")
                }
                writer.appendLine(">")
            }
            
            for (child in children) {
                nodeToXML(child, writer)
            }

            writer.appendLine("</$name>")
        } else {
            writer.append("<$name")
            for (pair in properties) {
                writer.append(" ${pair.key}=\"${pair.value}\"")
            }
            writer.appendLine("/>")
        }
    }

}