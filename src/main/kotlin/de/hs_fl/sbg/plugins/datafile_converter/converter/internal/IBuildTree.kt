package de.hs_fl.sbg.plugins.datafile_converter.converter.internal

interface IBuildTree {
    /**
     * Adds a new Node to the currently active Node and makes it the current Node
     *
     * @param name Name of the new Node
     * @return The worked on instance of [IBuildTree]
     **/
    fun newNode(name: String): TreeBuilder

    /**
     * Makes a current Nodes parent the current Node
     *
     * @return The worked on instance of [IBuildTree]
     **/
    fun moveOut(): TreeBuilder

    /**
     * Adds a new Property to the currently active Node
     *
     * @param key The Name of the property
     * @param value The Value of the property
     * @return The worked on instance of [IBuildTree]
     **/
    fun addProperty(key: String, value: Any?): TreeBuilder

    /**
     * Builds and return the [Tree]
     *
     * @return The finished [Tree]
     **/
    fun build(): Tree
}