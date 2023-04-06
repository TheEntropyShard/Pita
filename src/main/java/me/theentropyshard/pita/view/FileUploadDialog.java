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

import javax.swing.*;
import java.awt.*;

public class FileUploadDialog extends JDialog {
    public FileUploadDialog(Frame frame) {
        super(frame, "Прикрепите файл", true);

        JPanel panel = new JPanel(new FlowLayout());

        SimpleButton chooseFileButton = new SimpleButton("Выбрать файл");
        chooseFileButton.setRound(true);

        panel.add(chooseFileButton);

        GradientLabel fileNameLabel = new GradientLabel("", UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);

        panel.add(fileNameLabel, BorderLayout.CENTER);

        this.add(panel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        SimpleButton addFileButton = new SimpleButton("Прикрепить файл");
        addFileButton.setRound(true);

        SimpleButton cancelButton = new SimpleButton("Отменить");
        cancelButton.setRound(true);

        buttonsPanel.add(addFileButton);
        buttonsPanel.add(cancelButton);

        this.add(buttonsPanel, BorderLayout.SOUTH);
    }
}
