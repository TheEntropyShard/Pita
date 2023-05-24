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
import me.theentropyshard.pita.netschoolapi.NetSchoolAPI_old;
import me.theentropyshard.pita.utils.Utils;
import me.theentropyshard.pita.view.component.PGradientLabel;
import me.theentropyshard.pita.view.component.PSimpleButton;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Header extends JPanel {
    private final PGradientLabel infoLabel;
    private final PGradientLabel schoolNameLabel;

    private final PGradientLabel currentYearLabel;
    private final PGradientLabel usernameLabel;
    private final PSimpleButton mailButton;
    private final PGradientLabel exitLabel;

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
        /*this.usernameLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("Show my settings");
            }
        });*/

        this.exitLabel = new PGradientLabel("Выход");
        this.exitLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.exitLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));

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
        topPanel.add(new PGradientLabel("Сетевой город. Образование") {{
            this.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
        }}, "grow");
        topPanel.add(this.infoLabel, "wrap");
        topPanel.add(this.schoolNameLabel, "grow");
        topPanel.add(panel);
        this.add(topPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 4));
        bottomPanel.setBackground(tm.getColor("mainColor"));
        bottomPanel.add(new PSimpleButton("Дневник") {{
            /*this.addActionListener(e -> {
                StudentView mp = View.getView().getMainPanel();
                DiaryView diaryView = mp.getDiaryPanel();
                if (!diaryView.isVisible()) {
                    diaryView.loadData();
                    mp.getContentLayout().show(
                            mp.getContentPanel(),
                            DiaryView.class.getSimpleName()
                    );
                }
            });*/
        }});
        bottomPanel.add(new PSimpleButton("Отчеты") {{
            /*this.addActionListener(e -> {
                StudentView mp = View.getView().getMainPanel();
                ReportsPanel reportsPanel = mp.getReportsPanel();
                if (!reportsPanel.isVisible()) {
                    reportsPanel.loadData();
                    mp.getContentLayout().show(
                            mp.getContentPanel(),
                            ReportsPanel.class.getSimpleName()
                    );
                }
            });*/
        }});
        this.mailButton = new PSimpleButton("Почта") {{
            /*this.addActionListener(e -> {
                StudentView mp = View.getView().getMainPanel();
                MailPanel mailPanel = mp.getMailPanel();
                if (!mailPanel.isVisible()) {
                    mailPanel.loadData();
                    mp.getContentLayout().show(
                            mp.getContentPanel(),
                            MailPanel.class.getSimpleName()
                    );
                }
            });*/
        }};
        bottomPanel.add(this.mailButton);
        bottomPanel.add(new PSimpleButton("Объявления") {{
            /*this.addActionListener(e -> {
                StudentView mp = View.getView().getMainPanel();
                AnnouncementsView annPanel = mp.getAnnouncementsView();
                if (!annPanel.isVisible()) {
                    annPanel.loadData();
                    mp.getContentLayout().show(
                            mp.getContentPanel(),
                            AnnouncementsView.class.getSimpleName()
                    );
                }
            });*/
        }});
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    public void loadData() {
        int umc = 0;
        try {
            umc = NetSchoolAPI_old.I.getUnreadMessagesCount();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (umc > 0) {
            this.mailButton.setText("Почта - " + umc + " непрочитанных");
        } else {
            this.mailButton.setText("Почта");
        }

        this.schoolNameLabel.setText(NetSchoolAPI_old.I.getSchool().getShortName());

        try {
            this.currentYearLabel.setText("текущий " + NetSchoolAPI_old.I.getYearlist()[0].name + " уч.год");
        } catch (IOException e) {
            e.printStackTrace();
            this.currentYearLabel.setText("ОШИБКА");
        }

        this.usernameLabel.setText(NetSchoolAPI_old.I.getStudentName());

        int num = 0;
        try {
            num = NetSchoolAPI_old.I.getActiveSessions().size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.infoLabel.setText(Utils.getTodaysDateRussian() + " - В системе работает " + num + " чел.");
    }

    public PSimpleButton getMailButton() {
        return this.mailButton;
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
}
