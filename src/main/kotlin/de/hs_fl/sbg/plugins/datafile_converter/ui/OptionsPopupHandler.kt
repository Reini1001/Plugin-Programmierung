package de.hs_fl.sbg.plugins.datafile_converter.ui

import com.intellij.openapi.ui.popup.JBPopupFactory
import java.awt.Dimension
import javax.swing.JPanel

class OptionsPopupHandler {
    companion object {
        fun makeOptionsPopup(content: JPanel) {
            val builder = JBPopupFactory.getInstance().createComponentPopupBuilder(content, null)
            builder.setTitle("Options")
                .setMinSize(Dimension(400, 300))
                .setMovable(true)
                .createPopup()
                .showInFocusCenter()
        }
    }
}