package de.hs_fl.sbg.plugins.datafile_converter.ui

import de.hs_fl.sbg.plugins.datafile_converter.converter.EFileType
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JButton
import javax.swing.JCheckBox
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingUtilities

class OptionsPanel(
    private val convertTo: EFileType,
    private val convertFrom: EFileType
) : JPanel(), ActionListener {
    private var keepOgFileCheckBox = JCheckBox()
    private var cancelButton = JButton("Cancel")
    private var confirmButton = JButton("Confirm")

    val keepOgFile: Boolean
        get() = keepOgFileCheckBox.isSelected

    init {
        layout = GridBagLayout()

        val c = GridBagConstraints()
        c.fill = GridBagConstraints.HORIZONTAL
        c.ipadx = 5
        c.ipady = 5

        c.gridx = 0
        c.gridy = 0
        add(JLabel("Keep original file?"), c)

        c.gridx = 1
        add(keepOgFileCheckBox, c)

        c.gridx = 0
        c.gridy = 1
        add(cancelButton, c)
        cancelButton.addActionListener(this)

        c.gridx = 1
        c.gridy = 1
        add(confirmButton, c)
        confirmButton.addActionListener(this)
    }

    override fun actionPerformed(e: ActionEvent?) {
        when (e?.source) {
            cancelButton -> {
                SwingUtilities.getWindowAncestor(this).dispose()
            }
            confirmButton -> {
                SwingUtilities.getWindowAncestor(this).dispose()
                TODO("Not yet implemented")
            }
            else -> {}
        }
    }

}