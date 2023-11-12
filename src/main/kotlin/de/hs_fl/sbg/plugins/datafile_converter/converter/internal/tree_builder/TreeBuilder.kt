package de.hs_fl.sbg.plugins.datafile_converter.converter.internal.tree_builder

import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.Node
import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.Tree
import java.util.*

open class TreeBuilder {
    protected var nodes: Stack<Node> = Stack()

    /**
     * Adds a new [Node] to the [Tree] and adds it to the [Stack]
     *
     * @param name Name of the new Node
     * @return The worked on instance of [TreeBuilder]
     **/
    fun newNode(name: String) = apply {
        val newNode = Node(name)
        if(!nodes.empty()) nodes.peek().addChild(newNode)
        nodes.push(newNode)
    }

    /**
     * Pops the top [Node] from the [Stack]
     *
     * @return The worked on instance of [TreeBuilder]
     **/
    fun moveOut() = apply {
        if (nodes.size > 1) nodes.pop()
    }

    /**
     * Adds a new Property to the top [Node] on the [Stack]
     *
     * @param key The Name of the property
     * @param value The Value of the property
     * @return The worked on instance of [TreeBuilder]
     **/
    fun addProperty(key: String, value: Any?) = apply {
        nodes.peek().addProperty(key, value)
    }

    /**
     * Builds and returns the [Tree]
     **/
    open fun build(): Tree {
        return Tree(nodes.firstElement())
    }
}