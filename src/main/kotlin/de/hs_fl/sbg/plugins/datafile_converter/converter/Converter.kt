package de.hs_fl.sbg.plugins.datafile_converter.converter

import com.intellij.psi.PsiFile
import de.hs_fl.sbg.plugins.datafile_converter.converter.from.ConvertFromJSON
import de.hs_fl.sbg.plugins.datafile_converter.converter.from.ConvertFromXML
import de.hs_fl.sbg.plugins.datafile_converter.converter.from.IConvertFrom
import de.hs_fl.sbg.plugins.datafile_converter.converter.to.ConverterToJSON
import de.hs_fl.sbg.plugins.datafile_converter.converter.to.ConverterToXML
import de.hs_fl.sbg.plugins.datafile_converter.converter.to.IConvertTo

class Converter() {
    companion object {
        val instance: Converter by lazy { Converter() }
    }

    fun convert(from: PsiFile, to: EFileType) {
        val converterFrom: IConvertFrom = when(EFileType.valueOf(from.fileType.name)) {
            EFileType.XML -> ConvertFromXML()
            EFileType.JSON -> ConvertFromJSON()
        }

        val converterTo: IConvertTo = when(to) {
            EFileType.XML -> ConverterToXML()
            EFileType.JSON -> ConverterToJSON()
        }

        converterTo.convertAndWrite(
            converterFrom.convert(
                converterFrom.readFile(from.originalFile.virtualFile.path)
            ),
            from.containingDirectory.virtualFile.path + "/" + from.virtualFile.nameWithoutExtension
        )
    }
}