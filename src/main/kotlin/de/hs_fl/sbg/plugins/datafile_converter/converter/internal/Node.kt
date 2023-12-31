package de.hs_fl.sbg.plugins.datafile_converter.converter.internal

import org.apache.commons.lang3.tuple.MutablePair
import kotlin.collections.Map.Entry
import kotlin.collections.MutableMap.MutableEntry

class Node(var name: String) {

    constructor(name: String, properties: MutableSet<MutableEntry<String, Any?>>) : this(name) {
        this.properties = properties
    }

    constructor(name: String, children: MutableList<Node>) : this(name) {
        this.children = children
    }

    constructor(name: String, properties: MutableSet<MutableEntry<String, Any?>>, children: MutableList<Node>) : this(name) {
        this.properties = properties
        this.children = children
    }

    private var properties: MutableSet<MutableEntry<String, Any?>> = mutableSetOf()

    private var children: MutableList<Node> = mutableListOf()

    /**
     * Return all properties as a [Set]
     **/
    fun getProperties(): Set<Entry<String, Any?>> {
        return properties.map { it }.toSet()
    }

    /**
     * Returns all children as a [List]
     **/
    fun getChildren(): List<Node> {
        return children.toList()
    }

    /**
     * Adds a new property to the [Node]
     **/
    fun addProperty(key: String, value: Any?) {
        properties.add(MutablePair(key, value))
    }

    /**
     * Adds a new child to the [Node]
     **/
    fun addChild(child: Node) {
        children.add(child)
    }

    /**
     * Removes a child from [Node]
     **/
    fun removeChild(child: Node) {
        children.remove(child)
    }
}