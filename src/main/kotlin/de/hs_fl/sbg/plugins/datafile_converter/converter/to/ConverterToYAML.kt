package de.hs_fl.sbg.plugins.datafile_converter.converter.to

import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.Node
import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.Tree
import kotlinx.serialization.json.put
import java.io.BufferedWriter
import java.io.File

class ConverterToYAML : IConvertTo {
    override fun convertAndWrite(tree: Tree, pathFileName: String) {
        val outputFile = File("${pathFileName}.yaml")

        if (!outputFile.exists()) outputFile.createNewFile()
        val writer = outputFile.bufferedWriter()
        writer.write("---")
        writer.newLine()

        nodesToYaml(tree.root, writer, "")

        writer.close()
    }

    private fun nodesToYaml(curNode: Node, writer: BufferedWriter, prefix: String) {
        val nextPrefix = "$prefix  "
        val properties = curNode.getProperties()
        val children = curNode.getChildren()
        val name = curNode.name

        // Properties
        writer.appendLine("${prefix}${name}:")
        for (pair in properties) {
            writer.appendLine("${nextPrefix}${pair.key}: ${getYamlValue(pair.value)}")
        }

        // Children
        if (children.size > 1) checkAndCorrectDuplicateNames(curNode)
        for (child in children) {
            nodesToYaml(child, writer, nextPrefix)
        }
    }

    private fun escapeSpecialChars(input: String): String {
        return "\"${
            input.replace("\\", "\\\\")
                .replace("\n", "\\n")
                .replace("\t", "\\t")
                .replace("\"", "\\u0022")
                .replace("\'", "\\u0027")
        }\""
    }

    private fun getYamlValue(value: Any?): String {
        return when (value) {
            null -> "~"
            is Int, is Long, is Float, is Double -> "$value"
            // Booleans start with an upper Case letter in Yaml
            is Boolean -> {
                val boolString = "$value"
                boolString[0].uppercaseChar() + boolString.substring(1)
            }

            is String -> escapeSpecialChars(value)
            // Technically speaking, this else will never execute, but we're keeping it to be safe.
            else -> escapeSpecialChars("$value")
        }
    }

    private fun checkAndCorrectDuplicateNames(node: Node) {
        val children = node.getChildren()
        var names = mutableListOf<String>()
        for (child in children) {
            names.add(child.name)
        }

        // Check for duplicates. If there are none, we don't need to rename.
        var shouldRename = false
        // Keep renaming duplicates, until there are no more.
        while (hasDuplicates(names)) {
            shouldRename = true
            names = renameDuplicates(names).toMutableList()
        }

        // If at least one duplicate was found, rename the names of the children.
        if (shouldRename) {
            children.zip(names).forEach { pair ->
                pair.first.name = pair.second
            }
        }
    }

    private fun hasDuplicates(inputList: List<String>): Boolean {
        val seen = mutableSetOf<String>()

        for (element in inputList) {
            if (!seen.add(element)) {
                // Element is already in the set, indicating a duplicate
                return true
            }
        }

        // No duplicates found
        return false
    }

    private fun renameDuplicates(inputList: List<String>): List<String> {
        val resultMap = mutableMapOf<String, Int>()

        return inputList.map { original ->
            var newName = original
            var count = resultMap[original] ?: 0

            while (resultMap.keys.contains(newName)) {
                count++
                newName = "${original}_${count}"
            }

            resultMap[original] = count
            newName
        }
    }

}