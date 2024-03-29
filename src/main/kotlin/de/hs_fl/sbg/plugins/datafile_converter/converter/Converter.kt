package de.hs_fl.sbg.plugins.datafile_converter.converter

import com.intellij.psi.PsiFile
import de.hs_fl.sbg.plugins.datafile_converter.converter.from.ConverterFromCSV
import de.hs_fl.sbg.plugins.datafile_converter.converter.from.ConverterFromJSON
import de.hs_fl.sbg.plugins.datafile_converter.converter.from.ConverterFromXML
import de.hs_fl.sbg.plugins.datafile_converter.converter.from.IConvertFrom
import de.hs_fl.sbg.plugins.datafile_converter.converter.to.ConverterToJSON
import de.hs_fl.sbg.plugins.datafile_converter.converter.to.ConverterToXML
import de.hs_fl.sbg.plugins.datafile_converter.converter.to.ConverterToYAML
import de.hs_fl.sbg.plugins.datafile_converter.converter.to.IConvertTo
import kotlin.IllegalArgumentException
import kotlin.io.path.Path
import kotlin.io.path.deleteIfExists

class Converter() {
    companion object {
        val instance: Converter by lazy { Converter() }
    }

    /**
     * Converts File from One Type to another
     *
     *  @param from File from witch should be converted
     *  @param to FileType to witch should be converted
     **/
    fun convert(from: PsiFile, to: EFileType, keepOgFile: Boolean) {
        val converterFrom: IConvertFrom = when(EFileType.valueOf(from.fileType.name)) {
            EFileType.XML -> ConverterFromXML()
            EFileType.JSON -> ConverterFromJSON()
            EFileType.CSV -> ConverterFromCSV()
            else -> throw IllegalArgumentException("That Filetype is export-only") //This probably won't occur ever, but better safe than sorry
        }

        val converterTo: IConvertTo = when(to) {
            EFileType.XML -> ConverterToXML()
            EFileType.JSON -> ConverterToJSON()
            EFileType.YAML -> ConverterToYAML()
            else -> throw IllegalArgumentException("That Filetype is import-only") //This probably won't occur ever, but better safe than sorry
        }

        converterTo.convertAndWrite(
            converterFrom.convert(
                converterFrom.readFile(from.originalFile.virtualFile.path)
            ),
            from.containingDirectory.virtualFile.path + "/" + from.virtualFile.nameWithoutExtension
        )

        if (!keepOgFile) Path(from.virtualFile.path).deleteIfExists()
    }
}