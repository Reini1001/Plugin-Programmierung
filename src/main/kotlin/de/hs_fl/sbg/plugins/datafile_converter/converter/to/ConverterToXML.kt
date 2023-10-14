package de.hs_fl.sbg.plugins.datafile_converter.converter.to

import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.Node
import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.Tree
import java.io.BufferedWriter
import java.io.File

class ConverterToXML : IConvertTo {
    override fun convertAndWrite(tree: Tree, pathFileName: String) {
        val outputFile = File("$pathFileName.xml")

        if (!outputFile.exists()) outputFile.createNewFile()
        val writer = outputFile.bufferedWriter()
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
        writer.newLine()

        nodeToXML(tree.root, writer)

        writer.close()
    }

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