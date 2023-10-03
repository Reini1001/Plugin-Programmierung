package de.hs_fl.sbg.plugins.datafile_converter.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.ui.popup.JBPopupFactory

class GetFiletypeNameAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val filetypeName = e.getData(CommonDataKeys.PSI_FILE)?.fileType?.name

        val popup = JBPopupFactory.getInstance().createConfirmation(filetypeName, "Ja", "Nein", null, null, 0)
        popup.showInFocusCenter()
    }
}