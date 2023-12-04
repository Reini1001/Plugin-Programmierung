package de.hs_fl.sbg.plugins.datafile_converter.actions

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import de.hs_fl.sbg.plugins.datafile_converter.converter.EFileType
import de.hs_fl.sbg.plugins.datafile_converter.ui.OptionsPanel
import de.hs_fl.sbg.plugins.datafile_converter.ui.OptionsPopupHandler

class ConvertToYAMLAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val popupPanel = OptionsPanel(EFileType.YAML, e.getData(CommonDataKeys.PSI_FILE)!!)

        OptionsPopupHandler.makeOptionsPopup(popupPanel)
    }

    override fun update(e: AnActionEvent) {
        super.update(e)

        val filetypeName = e.getData(CommonDataKeys.PSI_FILE)?.fileType?.name

        e.presentation.isVisible = filetypeName != null && "YAML" != filetypeName

    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }
}