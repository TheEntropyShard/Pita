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
import sun.nio.ch.Net;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Header extends JPanel {
    private final JPanel topPanel;
    private final JPanel bottomPanel;

    private final GradientLabel infoLabel;
    private final GradientLabel schoolNameLabel;
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
        this.schoolNameLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 16));
        this.schoolNameLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.schoolNameLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

            }
        });

        panel = new JPanel() {{
            this.setBackground(Color.WHITE);
        }};

        this.topPanel = new JPanel() {{
            this.setLayout(new MigLayout("nogrid, fillx", "[]", ""));
            this.add(new GradientLabel("Сетевой город. Образование", UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN) {{
                this.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
            }}, "grow");
            this.add(infoLabel, "wrap");
            this.add(schoolNameLabel, "grow");
            this.add(panel);
        }};
        //this.topPanel.setBorder(new LineBorder(Color.RED, 1));
        this.topPanel.setBackground(Color.WHITE);
        this.add(this.topPanel, BorderLayout.CENTER);

        this.bottomPanel = new JPanel(new GridLayout(1, 3));
        //this.bottomPanel.setBorder(new LineBorder(Color.RED, 1));
        this.bottomPanel.setBackground(Color.WHITE);
        this.bottomPanel.add(new JButton("hello"));
        this.bottomPanel.add(new JButton("maybe"));
        this.bottomPanel.add(new JButton("apple"));
        this.add(this.bottomPanel, BorderLayout.SOUTH);
    }

    public void loadData() {
        try {
            panel.add(new GradientLabel("текущий " + NetSchoolAPI.I.getYearlist()[0].name + " уч.год",
                    UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN) {{
                this.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
            }});
        } catch (IOException e) {
            e.printStackTrace();
        }
        panel.add(new JSeparator(JSeparator.VERTICAL) {{
            this.setPreferredSize(new Dimension(5, 20));
        }});
        try {
            MySettings mySettings = NetSchoolAPI.I.getMySettings();
            panel.add(new GradientLabel(mySettings.firstName + " " + mySettings.lastName,
                    UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN) {{
                this.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
                this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                this.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        System.out.println("Show my settings");
                    }
                });
            }});
        } catch (IOException e) {
            e.printStackTrace();
        }
        panel.add(new JSeparator(JSeparator.VERTICAL) {{
            this.setPreferredSize(new Dimension(5, 20));
        }});
        panel.add(new GradientLabel("Выход", UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN) {{
            this.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    NetSchoolAPI.I.logout();
                    View.getView().getRootLayout().show(View.getView().getRoot(), LoginPanel.class.getSimpleName());
                }
            });
        }});

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
