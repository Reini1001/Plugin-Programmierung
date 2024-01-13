package de.hs_fl.sbg.plugins.datafile_converter.converter.to

import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.Node
import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.Tree
import kotlinx.serialization.json.put
import java.io.BufferedWriter
import java.io.File

class ConverterToYAML : IConvertTo {

    /**
     * Converts the [Tree] to a set of Strings in a [BufferedWriter] and writes it in an YAML-file at given path
     * @param[tree] The [Tree] containing the data
     * @param[pathFileName] The Path of the file, including the name, excluding the file extension
     */
    override fun convertAndWrite(tree: Tree, pathFileName: String) {
        val outputFile = File("${pathFileName}.yaml")

        if (!outputFile.exists()) outputFile.createNewFile()
        val writer = outputFile.bufferedWriter()
        writer.write("---")
        writer.newLine()

        nodesToYaml(tree.root, writer, "")

        writer.close()
    }

    /**
     * Recursively converts the given [Node] and all of its children to YAML
     * @param[curNode] The [Node] that will be converted
     * @param[writer] The [BufferedWriter] that is writing the [File].
     * @param[prefix] The prefix for every line. Is used for correct indentation. Use "" on first call.
     */
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

    /**
     * Escapes special chars, that would otherwise cause problems in YAML
     * @param[input] The String to escape
     * @return The escaped String
     */
    private fun escapeSpecialChars(input: String): String {
        return "\"${
            input.replace("\\", "\\\\")
                .replace("\n", "\\n")
                .replace("\t", "\\t")
                .replace("\"", "\\u0022")
                .replace("\'", "\\u0027")
        }\""
    }

    /**
     * Takes a given value and prepares it to be used in YAML, escaping special chars if needed
     * @param[value] The value to be used.
     * @return The prepared value, as a String.
     */
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

    /**
     * Checks if there are any duplicate names in the Node and renames them, if any are found,
     * since this would otherwise cause an issue in YAML. Only changes the Node, if there are duplicates.
     * @param[node] The node to check and potentially correct.
     */
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

    /**
     * Helper-function for use in [checkAndCorrectDuplicateNames].
     * Takes a list of Strings and returns True if any duplicates are found.
     * @param[inputList] The list to check for duplicates.
     * @return True if duplicates are found, False if none are found.
     */
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

    /**
     * Helper-function for use in [checkAndCorrectDuplicateNames].
     * Will take a list of Strings and rename the duplicates.
     *
     * This process follows this pattern: name, name_1, name_2, name_3, ...
     * @param[inputList] The list, which is changed.
     * @return The changed list.
     */
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
            return@map newName
        }
    }

}