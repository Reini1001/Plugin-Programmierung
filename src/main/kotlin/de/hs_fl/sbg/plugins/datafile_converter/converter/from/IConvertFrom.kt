package de.hs_fl.sbg.plugins.datafile_converter.converter.from

import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.Tree
import java.io.File

interface IConvertFrom {

    /**
     * Returns [File] for path.
     */
    fun readFile(path: String): File

    /**
     * Converts [File] to [Tree]
     */
    fun convert(file: File) : Tree

}