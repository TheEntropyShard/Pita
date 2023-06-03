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

import me.theentropyshard.pita.Pita;
import me.theentropyshard.pita.view.component.PGradientLabel;
import me.theentropyshard.pita.view.component.PSimpleButton;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class Header extends JPanel {
    private final PGradientLabel infoLabel;
    private final PGradientLabel schoolNameLabel;
    private final PGradientLabel currentYearLabel;
    private final PGradientLabel usernameLabel;
    private final PGradientLabel exitLabel;

    private final PSimpleButton diaryButton;
    private final PSimpleButton reportsButton;
    private final PSimpleButton mailButton;
    private final PSimpleButton announcementsButton;

    public Header() {
        ThemeManager tm = Pita.getPita().getThemeManager();

        this.setLayout(new BorderLayout());
        this.setBackground(tm.getColor("mainColor"));

        this.infoLabel = new PGradientLabel();
        this.infoLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
        this.infoLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        this.schoolNameLabel = new PGradientLabel();
        this.schoolNameLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 16));
        this.schoolNameLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        this.currentYearLabel = new PGradientLabel();
        this.currentYearLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));

        this.usernameLabel = new PGradientLabel();
        this.usernameLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.usernameLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));

        this.exitLabel = new PGradientLabel("Выход");
        this.exitLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.exitLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));

        this.diaryButton = new PSimpleButton("Дневник");
        this.reportsButton = new PSimpleButton("Отчеты");
        this.mailButton = new PSimpleButton("Почта");
        this.announcementsButton = new PSimpleButton("Объявления");

        JPanel panel = new JPanel();
        panel.setBackground(tm.getColor("mainColor"));
        panel.add(this.currentYearLabel);
        panel.add(new JSeparator(JSeparator.VERTICAL) {{
            this.setPreferredSize(new Dimension(5, 20));
        }});
        panel.add(this.usernameLabel);
        panel.add(new JSeparator(JSeparator.VERTICAL) {{
            this.setPreferredSize(new Dimension(5, 20));
        }});
        panel.add(this.exitLabel);

        JPanel topPanel = new JPanel(new MigLayout("nogrid, fillx", "[]", ""));
        topPanel.setBackground(tm.getColor("mainColor"));
        PGradientLabel sgoLabel = new PGradientLabel("Сетевой город. Образование");
        sgoLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
        topPanel.add(sgoLabel, "grow");
        topPanel.add(this.infoLabel, "wrap");
        topPanel.add(this.schoolNameLabel, "grow");
        topPanel.add(panel);
        this.add(topPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 4));
        bottomPanel.setBackground(tm.getColor("mainColor"));
        bottomPanel.add(this.diaryButton);
        bottomPanel.add(this.reportsButton);
        bottomPanel.add(this.mailButton);
        bottomPanel.add(this.announcementsButton);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    public PGradientLabel getSchoolNameLabel() {
        return this.schoolNameLabel;
    }

    public PGradientLabel getCurrentYearLabel() {
        return this.currentYearLabel;
    }

    public PGradientLabel getUsernameLabel() {
        return this.usernameLabel;
    }

    public PGradientLabel getInfoLabel() {
        return this.infoLabel;
    }

    public PGradientLabel getExitLabel() {
        return this.exitLabel;
    }

    public PSimpleButton getDiaryButton() {
        return this.diaryButton;
    }

    public PSimpleButton getReportsButton() {
        return this.reportsButton;
    }

    public PSimpleButton getMailButton() {
        return this.mailButton;
    }

    public PSimpleButton getAnnouncementsButton() {
        return this.announcementsButton;
    }
}
