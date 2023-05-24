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

package me.theentropyshard.pita.view.mail;

import me.theentropyshard.pita.Pita;
import me.theentropyshard.pita.view.BorderPanel;
import me.theentropyshard.pita.view.ThemeManager;
import me.theentropyshard.pita.view.View;
import me.theentropyshard.pita.view.component.PGradientLabel;
import me.theentropyshard.pita.view.component.PScrollBar;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class MailRecipientsDialog extends JDialog {
    private final JPanel panel;

    public MailRecipientsDialog(String... recipients) {
        super(View.getView().getFrame(), "Получатели", true);

        this.panel = new JPanel();
        ThemeManager tm = Pita.getPita().getThemeManager();
        this.panel.setBackground(tm.getColor("mainColor"));
        this.panel.setLayout(new MigLayout("fillx, flowy", "[fill]"));

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setViewportView(this.panel);
        scrollPane.setVerticalScrollBar(new PScrollBar());

        BorderPanel borderPanel = new BorderPanel();
        for(String recipient : recipients) {
            borderPanel.addComponent(new RecipientPanel(recipient));
        }

        this.panel.add(borderPanel);

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.add(scrollPane, BorderLayout.CENTER);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        ThemeManager tm = Pita.getPita().getThemeManager();
        this.panel.setBackground(tm.getColor("mainColor"));
        super.paint(g);
    }

    private static class RecipientPanel extends JPanel {
        private final JPanel panel;

        public RecipientPanel(String recipient) {
            PGradientLabel label = new PGradientLabel(recipient);
            label.setFont(new Font("JetBrains Mono", Font.BOLD, 14));

            this.panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            ThemeManager tm = Pita.getPita().getThemeManager();
            this.panel.setBackground(tm.getColor("mainColor"));
            this.panel.add(label);

            this.setBackground(tm.getColor("mainColor"));
            this.add(this.panel);
        }

        @Override
        protected void paintComponent(Graphics g) {
            ThemeManager tm = Pita.getPita().getThemeManager();
            this.panel.setBackground(tm.getColor("mainColor"));
            super.paintComponent(g);
        }
    }
}