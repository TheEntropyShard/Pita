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

import me.theentropyshard.pita.view.component.PGradientLabel;
import me.theentropyshard.pita.view.component.PSimpleButton;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class MessageDialog extends JDialog {
    private Result result = Result.CANCEL;

    public MessageDialog(String title, String message) {
        this(title, message, false);
    }

    public MessageDialog(String title, String message, boolean confirmDialog) {
        super(AppWindow.window, title, true);

        BorderPanel borderPanel = new BorderPanel();

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new MigLayout("fillx, flowy", "[fill]"));
        panel.add(borderPanel);

        PGradientLabel label = new PGradientLabel(message);
        label.setFont(new Font("JetBrains Mono", Font.BOLD, 16));
        borderPanel.addComponent(label);

        if(confirmDialog) {
            BorderPanel buttonsPanel = new BorderPanel();
            buttonsPanel.getInternalPanel().setLayout(new MigLayout("nogrid, fillx", "[right]", ""));

            PSimpleButton okButton = new PSimpleButton("ОК");
            okButton.setRoundCorners(true);
            okButton.addActionListener(e -> {
                this.result = Result.OK;
                this.dispose();
            });

            PSimpleButton cancelButton = new PSimpleButton("Отмена");
            cancelButton.setRoundCorners(true);
            cancelButton.addActionListener(e -> this.dispose());

            buttonsPanel.addComponent(cancelButton);
            buttonsPanel.addComponent(okButton);

            panel.add(buttonsPanel);
        }

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.add(panel, BorderLayout.CENTER);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public Result getResult() {
        return this.result;
    }

    public enum Result {
        OK,
        CANCEL
    }
}
