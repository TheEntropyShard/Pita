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
import me.theentropyshard.pita.view.component.SimpleButton;
import me.theentropyshard.pita.view.mail.MailPanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Header extends JPanel {
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
                View.getView().getFrame().getGlassPane().setVisible(true);

                JDialog dialog = new JDialog(View.getView().getFrame(), "Список пользователей в сети", true);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

                ActiveSessionsPanel panel = new ActiveSessionsPanel();

                dialog.add(panel);
                panel.loadData();
                panel.revalidate();

                Dimension preferredSize = panel.getPreferredSize();
                if(preferredSize.height > UIConstants.DEFAULT_HEIGHT) {
                    panel.setPreferredSize(new Dimension(preferredSize.width, UIConstants.DEFAULT_HEIGHT));
                }

                infoLabel.setText(Utils.getTodaysDateRussian() + " - В системе работает " + panel.getActiveSessions() + " чел.");

                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);

                View.getView().getFrame().getGlassPane().setVisible(false);
            }
        });

        this.schoolNameLabel = new GradientLabel(UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
        this.schoolNameLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.schoolNameLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 16));
        this.schoolNameLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                View.getView().getFrame().getGlassPane().setVisible(true);

                JDialog dialog = new JDialog(View.getView().getFrame(), "Карточка образовательной организации", true);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

                SchoolInfoPanel panel = new SchoolInfoPanel();

                dialog.add(panel);
                panel.loadData();
                panel.revalidate();

                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);

                View.getView().getFrame().getGlassPane().setVisible(false);
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

        JPanel topPanel = new JPanel(new MigLayout("nogrid, fillx", "[]", ""));
        topPanel.setBackground(Color.WHITE);
        topPanel.add(new GradientLabel("Сетевой город. Образование", UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN) {{
            this.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
        }}, "grow");
        topPanel.add(this.infoLabel, "wrap");
        topPanel.add(this.schoolNameLabel, "grow");
        topPanel.add(this.panel);
        this.add(topPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 4));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(new SimpleButton("Дневник") {{
            this.addActionListener(e -> {
                MainPanel mp = View.getView().getMainPanel();
                DiaryPanel diaryPanel = mp.getDiaryPanel();
                if(!diaryPanel.isVisible()) {
                    diaryPanel.loadData();
                    mp.getContentLayout().show(
                            mp.getContentPanel(),
                            DiaryPanel.class.getSimpleName()
                    );
                }
            });
        }});
        bottomPanel.add(new SimpleButton("Отчеты") {{
            this.addActionListener(e -> {
                MainPanel mp = View.getView().getMainPanel();
                ReportsPanel reportsPanel = mp.getReportsPanel();
                if(!reportsPanel.isVisible()) {
                    reportsPanel.loadData();
                    mp.getContentLayout().show(
                            mp.getContentPanel(),
                            ReportsPanel.class.getSimpleName()
                    );
                }
            });
        }});
        bottomPanel.add(new SimpleButton("Почта") {{
            this.addActionListener(e -> {
                MainPanel mp = View.getView().getMainPanel();
                MailPanel mailPanel = mp.getMailPanel();
                if(!mailPanel.isVisible()) {
                    mailPanel.loadData();
                    mp.getContentLayout().show(
                            mp.getContentPanel(),
                            MailPanel.class.getSimpleName()
                    );
                }
            });
        }});
        bottomPanel.add(new SimpleButton("Объявления") {{
            this.addActionListener(e -> {
                MainPanel mp = View.getView().getMainPanel();
                AnnouncementsPanel annPanel = mp.getAnnPanel();
                if(!annPanel.isVisible()) {
                    annPanel.loadData();
                    mp.getContentLayout().show(
                            mp.getContentPanel(),
                            AnnouncementsPanel.class.getSimpleName()
                    );
                }
            });
        }});
        this.add(bottomPanel, BorderLayout.SOUTH);
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
