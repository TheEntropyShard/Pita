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
import me.theentropyshard.pita.netschoolapi.models.MySettings;
import me.theentropyshard.pita.view.component.GradientLabel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Header extends JPanel {
    private final JPanel topPanel;
    private final JPanel bottomPanel;

    private final GradientLabel infoLabel;
    private final GradientLabel schoolNameLabel;

    private final GradientLabel currentYearLabel;
    private final GradientLabel usernameLabel;
    private final GradientLabel exitLabel;
    private final JPanel panel;

    public Header() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        this.infoLabel = new GradientLabel(UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
        this.infoLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
        this.infoLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.infoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    System.out.println(NetSchoolAPI.I.getActiveSessions());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        this.schoolNameLabel = new GradientLabel(UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
        this.schoolNameLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.schoolNameLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 16));
        this.schoolNameLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("Show school info");
            }
        });

        this.currentYearLabel = new GradientLabel(UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
        this.currentYearLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));

        this.usernameLabel = new GradientLabel(UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
        this.usernameLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.usernameLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
        this.usernameLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("Show my settings");
            }
        });

        this.exitLabel = new GradientLabel("Выход", UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
        this.exitLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.exitLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
        this.exitLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                NetSchoolAPI.I.logout();
                View.getView().getRootLayout().show(View.getView().getRoot(), LoginPanel.class.getSimpleName());
            }
        });

        this.panel = new JPanel();
        this.panel.setBackground(Color.WHITE);
        this.panel.add(this.currentYearLabel);
        this.panel.add(new JSeparator(JSeparator.VERTICAL) {{
            this.setPreferredSize(new Dimension(5, 20));
        }});
        this.panel.add(this.usernameLabel);
        this.panel.add(new JSeparator(JSeparator.VERTICAL) {{
            this.setPreferredSize(new Dimension(5, 20));
        }});
        this.panel.add(this.exitLabel);

        this.topPanel = new JPanel() {{
            this.setLayout(new MigLayout("nogrid, fillx", "[]", ""));
            this.add(new GradientLabel("Сетевой город. Образование", UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN) {{
                this.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
            }}, "grow");
            this.add(infoLabel, "wrap");
            this.add(schoolNameLabel, "grow");
            this.add(panel);
        }};
        this.topPanel.setBackground(Color.WHITE);
        this.add(this.topPanel, BorderLayout.CENTER);

        this.bottomPanel = new JPanel(new GridLayout(1, 4));
        this.bottomPanel.setBackground(Color.WHITE);
        this.bottomPanel.add(new JButton("Дневник"));
        this.bottomPanel.add(new JButton("Отчеты"));
        this.bottomPanel.add(new JButton("Почта"));
        this.bottomPanel.add(new JButton("Объявления"));
        this.add(this.bottomPanel, BorderLayout.SOUTH);
    }

    public void loadData() {
        this.schoolNameLabel.setText(NetSchoolAPI.I.getSchool().getShortName());

        try {
            this.currentYearLabel.setText("текущий " + NetSchoolAPI.I.getYearlist()[0].name + " уч.год");
        } catch (IOException e) {
            e.printStackTrace();
            this.currentYearLabel.setText("ОШИБКА");
        }

        try {
            MySettings mySettings = NetSchoolAPI.I.getMySettings();
            this.usernameLabel.setText(mySettings.firstName + " " + mySettings.lastName);
        } catch (IOException e) {
            e.printStackTrace();
            this.usernameLabel.setText("ОШИБКА");
        }

        int num = 0;
        try {
            num = NetSchoolAPI.I.getActiveSessions().size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.infoLabel.setText(Utils.getTodaysDateRussian() + " - В системе работает " + num + " чел.");
    }
}
