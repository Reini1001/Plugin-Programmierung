package de.hs_fl.sbg.plugins.datafile_converter.converter.to

import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.Node
import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.Tree
import kotlinx.serialization.json.*
import java.io.File

class ConverterToJSON : IConvertTo {
    override fun convertAndWrite(tree: Tree, pathFileName: String) {
        val outputFile = File("$pathFileName.json")

        val json = buildJsonObject { nodeAsJSON(tree.root, this) }

        if (!outputFile.exists()) outputFile.createNewFile()
        outputFile.writeText(json.toString())
    }

    private fun nodeAsJSON(curNode: Node, json: JsonObjectBuilder): JsonObjectBuilder {
        json.putJsonObject(curNode.name) {
            val properties = curNode.getProperties()
            val isPropertiesEmpty = properties.isEmpty()
            val children = curNode.getChildren()
            val isChildrenEmpty = children.isEmpty()

            //properties
            for (pair in properties) {
                val key = pair.key
                when (val value = pair.value) {
                    is Int -> put(key, value)
                    is Long -> put(key, value)
                    is Float -> put(key, value)
                    is Double -> put(key, value)
                    is Boolean -> put(key, value)
                    is String -> put(key, value)
                    else -> put(key, value.toString())
                }
            }

            if (isChildrenEmpty && isPropertiesEmpty) {
                // Empty children array, in case the object has no properties and no children, for whatever reason
                this.putJsonArray("children") {}
            } else if (!isChildrenEmpty) {
                //children
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