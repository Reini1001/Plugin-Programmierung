package de.hs_fl.sbg.plugins.datafile_converter.converter.internal.tree_builder

import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.Node
import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.Tree

class XMLTreeBuilder: TreeBuilder() {
    override fun build(): Tree {
        val root = nodes.firstElement()
        findChildProperties(root)
        return Tree(root)
    }

    /**
     * Works recursively through all [Node]s and checks whether they can and should be properties.
     * If a match is found it is converted.
     *
     * @param node Start [Node]
     **/
    private fun findChildProperties(node: Node) {
        val children = node.getChildren()

        children.forEach { child ->
            if (child.getChildren().isEmpty() &&
                !node.getProperties().any { entry -> entry.key == child.name} &&
                !children.any { otherChild -> otherChild !== child && otherChild.name == child.name }
                ) {
                child.getProperties()["_Content"]?.let {
                    node.addProperty(child.name, it)
                    node.removeChild(child)
                }
            }
        }

        children.forEach { findChildProperties(it) }
    }
}