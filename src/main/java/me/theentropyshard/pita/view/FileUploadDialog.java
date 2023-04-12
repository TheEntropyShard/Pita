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
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Objects;

public class FileUploadDialog extends JDialog {
    private File selectedFile;

    public FileUploadDialog(Frame frame) {
        super(frame, "Прикрепите файл", true);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new MigLayout("fillx, flowy", "[fill]"));

        BorderPanel chooseFilePanel = new BorderPanel();

        SimpleButton chooseFileButton = new SimpleButton("Выбрать файл");
        chooseFileButton.setRoundCorners(true);

        chooseFilePanel.addComponent(chooseFileButton);

        GradientLabel fileNameLabel = new GradientLabel("Имя файла...", PitaColors.DARK_COLOR, PitaColors.LIGHT_COLOR);
        fileNameLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));

        chooseFilePanel.addComponent(fileNameLabel);

        chooseFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if(result == JFileChooser.APPROVE_OPTION) {
                this.selectedFile = Objects.requireNonNull(fileChooser.getSelectedFile());
                fileNameLabel.setText(this.selectedFile.getName());
            }
        });

        panel.add(chooseFilePanel, BorderLayout.CENTER);

        BorderPanel buttonsPanel = new BorderPanel();
        buttonsPanel.getInternalPanel().setLayout(new MigLayout("nogrid, fillx", "[right]", ""));

        SimpleButton addFileButton = new SimpleButton("Прикрепить файл");
        addFileButton.setRoundCorners(true);
        addFileButton.addActionListener(e -> this.dispose());

        buttonsPanel.addComponent(addFileButton);

        panel.add(buttonsPanel);

        this.add(panel, BorderLayout.CENTER);

        this.getContentPane().setPreferredSize(new Dimension(628, 168));
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public File getSelectedFile() {
        return this.selectedFile;
    }
}
