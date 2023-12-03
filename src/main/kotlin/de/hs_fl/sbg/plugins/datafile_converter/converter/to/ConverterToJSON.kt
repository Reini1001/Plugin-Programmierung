package de.hs_fl.sbg.plugins.datafile_converter.converter.to

import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.Node
import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.Tree
import kotlinx.serialization.json.*
import java.io.File

class ConverterToJSON : IConvertTo {

    /**
     * Converts the [Tree] to a [JsonObject] and writes it in a JSON-file at given path
     * @param[tree] The [Tree] containing the data
     * @param[pathFileName] The Path of the [File], including the name, excluding the file extension
     */
    override fun convertAndWrite(tree: Tree, pathFileName: String) {
        val outputFile = File("${pathFileName}.json")

        val json = buildJsonObject { nodeAsJSON(tree.root, this) }

        if (!outputFile.exists()) outputFile.createNewFile()
        outputFile.writeText(json.toString())
    }

    /**
     * Recursively converts the given [Node] and all of its children to JSON
     * @param[curNode] The [Node] that will be converted
     * @param[json] The [JsonObjectBuilder] that the JSON is saved it.
     * @return[JsonObjectBuilder] The json, ready to be written to a file.
     */
    private fun nodeAsJSON(curNode: Node, json: JsonObjectBuilder): JsonObjectBuilder {
        json.putJsonObject(curNode.name) {
            // save commonly used function calls in a val
            val properties = curNode.getProperties()
            val isPropertiesEmpty = properties.isEmpty()
            val children = curNode.getChildren()
            val isChildrenEmpty = children.isEmpty()

            // properties. We need to check for the different types, for the smart-cast to work.
            // If we just put the value inside, it tries to cast to [JsonElement], which will not work.
            for (pair in properties) {
                val key = pair.key
                when (val value = pair.value) {
                    null -> put(key, null as String?)
                    is Int -> put(key, value)
                    is Long -> put(key, value)
                    is Float -> put(key, value)
                    is Double -> put(key, value)
                    is Boolean -> put(key, value)
                    is String -> put(key, value)
                    // Technically speaking, this else will never execute, but we're keeping it to be safe.
                    else -> put(key, value.toString())
                }
            }

            if (isChildrenEmpty && isPropertiesEmpty) {
                // Empty children array, in case the object has no properties and no children, for whatever reason
                this.putJsonArray("children") {}
            } else if (!isChildrenEmpty) {
                // recursively add the children
                this.putJsonArray("children") {
                    for (child in children) {
                        addJsonObject { nodeAsJSON(child, this) }
                    }
                }
            }
        }
        return json
    }
}