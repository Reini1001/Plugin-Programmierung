package de.hs_fl.sbg.plugins.datafile_converter.converter.from

import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.IBuildTree
import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.Tree
import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.TreeBuilder
import java.io.File

class ConvertFromXML: IConvertFrom {

    override fun readFile(path: String): File {
        val file = File(path)

        if (!file.exists()) TODO()

        return file
    }

    override fun convert(file: File): Tree {
        val builder = TreeBuilder()

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

                }
            }
        }

        return builder.build()
    }

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

    private fun builderInputFromString(input: String, builder: IBuildTree) {
        val parameter = input.split(" ")
        builder.newNode(parameter[0])

        for (i in 1 until parameter.size) {
            val keyValuePair = parameter[i].split("=")
            keyValuePair.forEach{ it.trim() }
            val key = keyValuePair[0]
            val value = keyValuePair[1].substring(1, keyValuePair[1].length - 1)

            when {
                value.toBooleanStrictOrNull() != null -> {
                    builder.addProperty(key, value.toBooleanStrict())
                }
                value.toIntOrNull() != null -> {
                    builder.addProperty(key, value.toInt())
                }
                value.toLongOrNull() != null -> {
                    builder.addProperty(key, value.toLong())
                }
                value.toDoubleOrNull() != null -> {
                    builder.addProperty(key, value.toDouble())
                }
                else -> {
                    builder.addProperty(key, value)
                }
            }
        }
    }
}