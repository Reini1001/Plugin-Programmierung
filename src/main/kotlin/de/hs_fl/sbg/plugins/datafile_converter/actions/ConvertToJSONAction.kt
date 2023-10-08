package de.hs_fl.sbg.plugins.datafile_converter.actions

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import de.hs_fl.sbg.plugins.datafile_converter.converter.EFileType
import de.hs_fl.sbg.plugins.datafile_converter.ui.OptionsPanel
import de.hs_fl.sbg.plugins.datafile_converter.ui.OptionsPopupHandler

class ConvertToJSONAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val popupPanel = OptionsPanel(EFileType.JSON, EFileType.valueOf(e.getData(CommonDataKeys.PSI_FILE)?.fileType?.name!!))

        OptionsPopupHandler.makeOptionsPopup(popupPanel)
    }

    override fun update(e: AnActionEvent) {
        super.update(e)

        val filetypeName = e.getData(CommonDataKeys.PSI_FILE)?.fileType?.name

        e.presentation.isVisible = filetypeName != null && "JSON" != filetypeName

    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }
}