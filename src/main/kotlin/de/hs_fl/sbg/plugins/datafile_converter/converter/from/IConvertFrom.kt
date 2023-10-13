package de.hs_fl.sbg.plugins.datafile_converter.converter.from

import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.Tree
import java.io.File

interface IConvertFrom {

    fun readFile(path: String): File

    fun convert(file: File) : Tree

}