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

public class MessageDialog extends JDialog {
    private Result result = Result.CANCEL;

    public MessageDialog(String title, String message) {
        this(title, message, false);
    }

    public MessageDialog(String title, String message, boolean confirmDialog) {
        super(View.getView().getFrame(), title, true);

        BorderPanel borderPanel = new BorderPanel();

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new MigLayout("fillx, flowy", "[fill]"));
        panel.add(borderPanel);

        GradientLabel label = new GradientLabel(message, UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
        label.setFont(new Font("JetBrains Mono", Font.BOLD, 16));
        borderPanel.addComponent(label);

        if(confirmDialog) {
            BorderPanel buttonsPanel = new BorderPanel();
            buttonsPanel.getInternalPanel().setLayout(new MigLayout("nogrid, fillx", "[right]", ""));

            SimpleButton okButton = new SimpleButton("ОК");
            okButton.setRound(true);
            okButton.addActionListener(e -> {
                this.result = Result.OK;
                this.dispose();
            });

            SimpleButton cancelButton = new SimpleButton("Отмена");
            cancelButton.setRound(true);
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
