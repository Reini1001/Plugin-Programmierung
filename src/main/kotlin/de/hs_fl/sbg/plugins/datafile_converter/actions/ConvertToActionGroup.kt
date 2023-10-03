package de.hs_fl.sbg.plugins.datafile_converter.actions

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.DefaultActionGroup

class ConvertToActionGroup : DefaultActionGroup() {
    private val validTypes = listOf("XML", "JSON")

    override fun update(e: AnActionEvent) {
        val filetypeName = e.getData(CommonDataKeys.PSI_FILE)?.fileType?.name

        e.presentation.isVisible= validTypes.contains(filetypeName)
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }
}