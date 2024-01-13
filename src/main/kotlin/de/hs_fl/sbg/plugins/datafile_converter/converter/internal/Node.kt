package de.hs_fl.sbg.plugins.datafile_converter.converter.internal

class Node(var name: String) {

    constructor(name: String, properties: MutableMap<String, Any?>) : this(name) {
        this.properties = properties
    }

    constructor(name: String, children: MutableSet<Node>) : this(name) {
        this.children = children
    }

    constructor(name: String, properties: MutableMap<String, Any?>, children: MutableSet<Node>) : this(name) {
        this.properties = properties
        this.children = children
    }

    private var properties: MutableMap<String, Any?> = mutableMapOf()

    private var children: MutableSet<Node> = mutableSetOf()

    /**
     * Return all properties as a [Set]
     **/
    fun getProperties(): Map<String, Any?> {
        return properties
    }

    /**
     * Returns all children as a [List]
     **/
    fun getChildren(): Set<Node> {
        return children
    }

    /**
     * Adds a new property to the [Node]
     **/
    fun addProperty(key: String, value: Any?) {
        properties[key] = value
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