package de.hs_fl.sbg.plugins.datafile_converter.converter.to

import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.Node
import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.Tree
import java.io.BufferedWriter
import java.io.File

class ConverterToXML : IConvertTo {

    /**
     * Converts the [Tree] to a set of Strings in a [BufferedWriter] and writes it in an XML-file at given path
     * @param[tree] The [Tree] containing the data
     * @param[pathFileName] The Path of the file, including the name, excluding the file extension
     */
    override fun convertAndWrite(tree: Tree, pathFileName: String) {
        val outputFile = File("${pathFileName}.xml")

        if (!outputFile.exists()) outputFile.createNewFile()
        val writer = outputFile.bufferedWriter()
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
        writer.newLine()

        nodesToXML(tree.root, writer)

        writer.close()
    }

    /**
     * Recursively converts the given [Node] and all of its children to XML
     * @param[curNode] The [Node] that will be converted
     * @param[writer] The [BufferedWriter] that is writing the [File].
     */
    private fun nodesToXML(curNode: Node, writer: BufferedWriter) {
        val properties = curNode.getProperties()
        val isPropertiesEmpty = properties.isEmpty()
        val children = curNode.getChildren()
        val isChildrenEmpty = children.isEmpty()
        val name = escapeSpecialChars(curNode.name)

        if (isChildrenEmpty && isPropertiesEmpty) {
            writer.appendLine("<$name/>")
        } else if (!isChildrenEmpty) {
            if (isPropertiesEmpty)
                writer.appendLine("<$name>")
            else {
                convertNodeWithProperties(name, properties, writer, false)
            }

            for (child in children) {
                nodesToXML(child, writer)
            }

            writer.appendLine("</$name>")
        } else {
            convertNodeWithProperties(name, properties, writer, true)
        }
    }

    /**
     * Helper function, that creates a single line with the name of the [Node] and it's properties.
     * @param[name] The name of the [Node]
     * @param[properties] The properties of the [Node], usually obtained using [Node.getProperties]
     * @param[writer] The [BufferedWriter] that is used to write to a File
     * @param[shouldCloseNode] Defines, weather or not the node should be closed on the same line. True, if there are no children, otherwise false
     */
    private fun convertNodeWithProperties(
        name: String,
        properties: Map<String, Any?>,
        writer: BufferedWriter,
        shouldCloseNode: Boolean
    ) {
        writer.append("<$name")
        for (pair in properties) {
            val value = if (pair.value != null) pair.value else "_NULL"
            writer.append(" ${pair.key}=\"${escapeSpecialChars(value.toString())}\"")
        }
        writer.appendLine(if (shouldCloseNode) "/>" else ">")
    }

    /**
    * Escapes special chars, that would otherwise cause problems in XML
    * @param[input] The String to escape
    * @return The escaped String
    */
    private fun escapeSpecialChars(input: String): String {
        return input.replace("\"", "&quot;")
            .replace("\'", "&apos;")
            .replace("<", "&lt;")
            .replace(">", "&rt;")
            .replace("&", "&amp;")
    }

}