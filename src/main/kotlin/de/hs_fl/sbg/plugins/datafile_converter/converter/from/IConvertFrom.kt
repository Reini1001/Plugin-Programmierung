package de.hs_fl.sbg.plugins.datafile_converter.converter.from

import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.Tree
import java.io.File

interface IConvertFrom {

    /**
     * @param path The Path to the [File] to be converted
     * @return File for the given path
     */
    fun readFile(path: String): File

    /**
     * @param file [File] to be converted
     * @return [Tree] created from [File] content
     */
    fun convert(file: File) : Tree

}