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

import me.theentropyshard.pita.Utils;
import me.theentropyshard.pita.netschoolapi.NetSchoolAPI;
import me.theentropyshard.pita.view.component.GradientLabel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.IOException;

public class Header extends JPanel {
    private final JPanel topPanel;
    private final JPanel bottomPanel;

    private final JLabel schoolNameLabel;
    private final JLabel infoLabel;

    public Header() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        this.schoolNameLabel = new JLabel();
        this.infoLabel = new JLabel();

        this.topPanel = new JPanel() {{
            this.setLayout(new MigLayout("nogrid, fillx", "[]", ""));
            this.add(new GradientLabel("Сетевой город. Образование", UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN), "grow");
            this.add(infoLabel, "wrap");
            this.add(schoolNameLabel);
        }};
        this.topPanel.setBorder(new LineBorder(Color.RED, 1));
        this.topPanel.setBackground(Color.WHITE);
        this.add(this.topPanel, BorderLayout.CENTER);

        this.bottomPanel = new JPanel(new GridLayout(1, 3));
        this.bottomPanel.setBorder(new LineBorder(Color.RED, 1));
        this.bottomPanel.setBackground(Color.WHITE);
        this.bottomPanel.add(new JButton("hello"));
        this.bottomPanel.add(new JButton("maybe"));
        this.bottomPanel.add(new JButton("apple"));
        this.add(this.bottomPanel, BorderLayout.SOUTH);
    }

    public void loadData() {
        this.schoolNameLabel.setText(NetSchoolAPI.I.getSchool().getShortName());
        int num = 0;
        try {
            num = NetSchoolAPI.I.getActiveSessions().size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.infoLabel.setText(Utils.getTodaysDateRussian() + " - В системе работает " + num + " чел.");
    }
}
