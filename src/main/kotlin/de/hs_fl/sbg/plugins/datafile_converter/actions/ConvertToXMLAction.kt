package de.hs_fl.sbg.plugins.datafile_converter.actions

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys

class ConvertToXMLAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        TODO("Not yet implemented")
    }

    override fun update(e: AnActionEvent) {
        super.update(e)

        val filetypeName = e.getData(CommonDataKeys.PSI_FILE)?.fileType?.name

        e.presentation.isVisible = filetypeName != null && "XML" != filetypeName

    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }
}