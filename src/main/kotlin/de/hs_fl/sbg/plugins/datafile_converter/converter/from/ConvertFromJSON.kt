package de.hs_fl.sbg.plugins.datafile_converter.converter.from

import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.Tree
import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.tree_builder.TreeBuilder
import kotlinx.serialization.json.*
import java.io.File

class ConvertFromJSON: IConvertFrom {
    override fun readFile(path: String): File {
        val file = File(path)

        if (!file.exists()) TODO()

        return file
    }

    override fun convert(file: File): Tree {
        val builder = TreeBuilder()
        builder.newNode("root")

        val json = Json.parseToJsonElement(file.readText()) as JsonObject

        builderInputFromJsonObject(json, builder)

        return builder.build()
    }

    /**
     * Adds a [JsonObject] to the [TreeBuilder] and calls the respective functions to add its children.
     **/
    private fun builderInputFromJsonObject(input: JsonObject, builder: TreeBuilder) {
        input.forEach { t, u ->

            when (u) {
                is JsonPrimitive -> {
                    builder.addProperty(t, ConvertFromUtils.toTypeOrDefault(u.content))
                }
                is JsonArray -> {
                    builder.newNode("Array").addProperty("name", t)
                    builderInputFromJsonArray(u, builder)
                    builder.moveOut()
                }
                is JsonObject -> {
                    builder.newNode(t)
                    builderInputFromJsonObject(u, builder)
                    builder.moveOut()
                }
            }
        }
    }

    /**
     * Adds a [JsonArray] to the [TreeBuilder] and calls the respective functions to add its children.
     **/
    private fun builderInputFromJsonArray(input: JsonArray, builder: TreeBuilder) {
        input.forEach {
            builder.newNode("Entry")
            when (it) {
                is JsonPrimitive -> {
                    builder.addProperty("value", ConvertFromUtils.toTypeOrDefault(it.content))
                }
                is JsonArray -> {
                    builder.newNode("Array").addProperty("name", it.toString())
                    builderInputFromJsonArray(it, builder)
                    builder.moveOut()
                }
                is JsonObject -> {
                    builder.newNode("EntryObject") // TODO: find / generate objektname
                    builderInputFromJsonObject(it, builder)
                    builder.moveOut()
                }
            }
            builder.moveOut()
        }
    }
}