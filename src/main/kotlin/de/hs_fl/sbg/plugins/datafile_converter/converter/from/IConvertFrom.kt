package de.hs_fl.sbg.plugins.datafile_converter.converter.from

import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.Tree

interface IConvertFrom {

    fun readFile(path: String): List<String>

    fun convert(data: List<String>) : Tree

}