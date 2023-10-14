package de.hs_fl.sbg.plugins.datafile_converter.converter.internal

interface IBuildTree {
    fun newNode(name: String): TreeBuilder
    fun moveOut(): TreeBuilder
    fun addProperty(key: String, value: Any): TreeBuilder
    fun build(): Tree
}