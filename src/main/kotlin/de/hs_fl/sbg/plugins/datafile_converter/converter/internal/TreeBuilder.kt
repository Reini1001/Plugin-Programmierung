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
        return Tree(nodes.firstElement())
    }

    private fun findChildProperties(node: Node) {
        val children = node.getChildren()

        children.forEach { child ->
            if (children.any { otherChild -> otherChild !== child && otherChild.name == child.name }) {

            }
        }




        children.forEach { findChildProperties(it) }
    }
}