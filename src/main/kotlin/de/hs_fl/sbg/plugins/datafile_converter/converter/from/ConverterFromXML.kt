package de.hs_fl.sbg.plugins.datafile_converter.converter.from

import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.Tree
import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.tree_builder.TreeBuilder
import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.tree_builder.XMLTreeBuilder
import java.io.File

class ConverterFromXML: IConvertFrom {

    override fun readFile(path: String): File {
        val file = File(path)

        if (!file.exists()) TODO()

        return file
    }

    override fun convert(file: File): Tree {
        val builder = XMLTreeBuilder()

        val tags = splitFileContent(file.readText())

        tags.forEach{
            when {
                it.startsWith("</") -> {
                    builder.moveOut()
                }
                it.startsWith("<?") -> {
                    return@forEach
                }
                it.startsWith("<") && it.endsWith("/>") -> {
                    builderInputFromString(it.substring(1, it.length - 2), builder)
                    builder.moveOut()
                }
                it.startsWith("<") -> {
                    builderInputFromString(it.substring(1, it.length - 1), builder)
                }
                else -> {
                    builder.addProperty("_Content", it.trim())
                }
            }
        }

        return builder.build()
    }

    /**
     * Splits the given XML-[String] into Node and Content parts.
     *
     * @param content XML-[String]
     **/
    private fun splitFileContent(content: String): List<String> {
        val outList: MutableList<String> = mutableListOf()
        var contentNew = content.trim()
        while (contentNew.isNotEmpty()) {
            val splitIndex = if (contentNew.substring(0, 1) == "<") contentNew.indexOf('>') + 1 else contentNew.indexOf('<')
            outList.add(contentNew.substring(0, splitIndex))
            contentNew = contentNew.substring(splitIndex).trim()
        }
        return outList
    }

    /**
     * Adds an XML-Node-[String] to the [TreeBuilder]
     **/
    private fun builderInputFromString(input: String, builder: TreeBuilder) {
        val parameter = input.split(" ")
        builder.newNode(parameter[0])

        for (i in 1 until parameter.size) {
            val keyValuePair = parameter[i].split("=")
            keyValuePair.forEach{ it.trim() }
            val key = keyValuePair[0]
            val value = keyValuePair[1].substring(1, keyValuePair[1].length - 1)

            builder.addProperty(key, ConverterFromUtils.toTypeOrDefault(value))
        }
    }
}