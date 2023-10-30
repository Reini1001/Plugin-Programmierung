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

        json.forEach { t, u ->
            println(u.javaClass)

            when (u) {
                is JsonPrimitive -> {
                    val converted = u as JsonPrimitive
                    builder.addProperty(t, ConvertFromUtils.toTypeOrDefault(u.content))
                }
                is JsonArray -> {
                    // TODO: check for Objects
                    builder.addProperty(t, u.toList() as List<Any>)
                }
                is JsonObject -> {

                }
                is JsonNull -> {
                    TODO() // builder.addProperty(t, null)
                }
            }
        }

        return builder.build()
    }
}