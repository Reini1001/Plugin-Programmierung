package de.hs_fl.sbg.plugins.datafile_converter.converter.from

import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.Tree
import de.hs_fl.sbg.plugins.datafile_converter.converter.internal.tree_builder.TreeBuilder
import java.io.File

class ConvertFromCSV : IConvertFrom {
    override fun readFile(path: String): File {
        val file = File(path)

        if (!file.exists()) TODO()

        return file
    }

    override fun convert(file: File): Tree {
        val builder = TreeBuilder()

        val lines = file.readLines()

        val columns = lines[0].split(",").map { it.trim() }
        val rows = lines.drop(1).map {line -> line.split(",").map { it.trim() }}

        rows.forEach { row ->
            builder.newNode("Entry")
            for (i in columns.indices) {
                builder.addProperty(columns[i], ConvertFromUtils.toTypeOrDefault(row[i]))
            }
            builder.moveOut()
        }

        return builder.build()
    }


}