package de.hs_fl.sbg.plugins.datafile_converter.converter.from

import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.Node
import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.Tree
import java.io.File
import java.util.*

class ConvertFromXML: IConvertFrom {
    override fun readFile(path: String): File {
        val file = File(path)

        if (!file.exists()) TODO()

        return file
    }

    override fun convert(file: File): Tree {
        val path: Stack<String> = Stack()
        var tree: Tree? = null
        var currentNode: Node? = null
        var pushToPath = false

        file.readText().trim().split(">").forEach {
            val node: Node

            when {
                it.startsWith("</") -> {
                    path.pop()
                    return@forEach
                }
                it.startsWith("<?") -> {
                    return@forEach
                }
                it.startsWith("<") && it.endsWith("/") -> {
                    node = nodeFromStrings(it.substring(1, it.length - 2).trim().split(" "))
                }
                it.startsWith("<") -> {
                    node = nodeFromStrings(it.substring(1).trim().split(" "))
                    pushToPath = true
                }
                else -> {
                    return@forEach
                }
            }

            if (tree == null) tree = Tree(node)
            if (currentNode == null) {
                var current: Node = tree?.root!!

                for (i in 1 until path.size ) {
                    current = current.getChildren().find { child -> child.name == path[i] }!!
                }

                currentNode = current
            }

            currentNode!!.addChild(node)

            if (pushToPath) {
                path.push(node.name)
                currentNode = null
            }
        }

        return tree!!
    }

    private fun nodeFromStrings(strings: List<String>): Node {
        val node = Node(strings[0])
        for (i in 1 until strings.size) {
            val keyValuePair = strings[i].split("=")
            keyValuePair.forEach { it.trim() }
            val key = keyValuePair[0]
            val value = keyValuePair[1].substring(1, keyValuePair[1].length - 1)

            when {
                value.toBooleanStrictOrNull() != null -> {
                    node.addProperty(key, value.toBooleanStrict())
                }
                value.toShortOrNull() != null -> {
                    node.addProperty(key, value.toShort())
                }
                value.toIntOrNull() != null -> {
                    node.addProperty(key, value.toInt())
                }
                value.toLongOrNull() != null -> {
                    node.addProperty(key, value.toLong())
                }
                value.toDoubleOrNull() != null -> {
                    node.addProperty(key, value.toDouble())
                }
                value.toFloatOrNull() != null -> {
                    node.addProperty(key, value.toFloat())
                }
                else -> {
                    node.addProperty(key, value)
                }
            }
        }

        return node
    }
}