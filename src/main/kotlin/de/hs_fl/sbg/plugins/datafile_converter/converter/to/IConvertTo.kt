package de.hs_fl.sbg.plugins.datafile_converter.converter.to

import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.Tree

interface IConvertTo {

    fun convertAndWrite(tree: Tree, pathFileName: String)
}