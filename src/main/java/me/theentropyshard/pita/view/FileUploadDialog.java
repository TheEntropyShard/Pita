/*      Pita. A simple desktop client for NetSchool by irTech
 *      Copyright (C) 2022-2023 TheEntropyShard
 *
 *      This program is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.theentropyshard.pita.view;

import me.theentropyshard.pita.view.component.GradientLabel;
import me.theentropyshard.pita.view.component.SimpleButton;
import me.theentropyshard.pita.view.mail.InfoPanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class FileUploadDialog extends JDialog {
    public FileUploadDialog(Frame frame) {
        super(frame, "Прикрепите файл", true);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new MigLayout("fillx, flowy", "[fill]"));

        InfoPanel chooseFilePanel = new InfoPanel();

        SimpleButton chooseFileButton = new SimpleButton("Выбрать файл");
        chooseFileButton.setRound(true);

        chooseFilePanel.addDataPanel(chooseFileButton);

        GradientLabel fileNameLabel = new GradientLabel("", UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);

        chooseFilePanel.addDataPanel(fileNameLabel);

        panel.add(chooseFilePanel, BorderLayout.CENTER);

        InfoPanel buttonsPanel = new InfoPanel();
        buttonsPanel.getInternalInfoPanel().setLayout(new MigLayout("fillx", "[right][right]", ""));

        SimpleButton addFileButton = new SimpleButton("Прикрепить файл");
        addFileButton.setRound(true);

        SimpleButton cancelButton = new SimpleButton("Отменить");
        cancelButton.setRound(true);

        buttonsPanel.addDataPanel(addFileButton);
        buttonsPanel.addDataPanel(cancelButton);

        panel.add(buttonsPanel);

        this.add(panel, BorderLayout.CENTER);
    }
}
