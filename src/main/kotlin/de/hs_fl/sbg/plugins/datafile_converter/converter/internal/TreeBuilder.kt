package de.hs_fl.sbg.plugins.datafile_converter.converter.internal

import java.util.Stack

class TreeBuilder: IBuildTree {
    private var nodes: Stack<Node> = Stack()

    override fun newNode(name: String) = apply {
        val newNode = Node(name)
        if(!nodes.empty()) nodes.peek().addChild(newNode)
        nodes.push(newNode)
    }

    override fun moveOut() = apply {
        if (nodes.size > 1) nodes.pop()
    }

    override fun addProperty(key: String, value: Any) = apply {
        nodes.peek().addProperty(key, value)
    }

    override fun build(): Tree {
        val root = nodes.firstElement()
        findChildProperties(root)
        return Tree(root)
    }

    private fun findChildProperties(node: Node) {
        val children = node.getChildren()

        children.forEach { child ->
            if (child.getChildren().isEmpty() &&
                !node.getProperties().any { entry -> entry.key == child.name} &&
                !children.any { otherChild -> otherChild !== child && otherChild.name == child.name }
                ) {
                child.getProperties().find { property -> property.key == "_Content" }?.let {
                    node.addProperty(child.name, it.value)
                    node.removeChild(child)
                }
            }
        }

        children.forEach { findChildProperties(it) }
    }
}