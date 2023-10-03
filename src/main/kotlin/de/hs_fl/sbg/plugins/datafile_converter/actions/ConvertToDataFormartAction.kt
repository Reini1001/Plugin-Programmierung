package de.hs_fl.sbg.plugins.datafile_converter.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.popup.JBPopupFactory

class ConvertToDataFormartAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val popup = JBPopupFactory.getInstance().createConfirmation("Funktioniert", "Ja", "Nein", null, null, 0)
        popup.showInFocusCenter()
    }
}