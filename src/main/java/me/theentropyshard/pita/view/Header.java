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
import me.theentropyshard.pita.Utils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Header extends JPanel {
    private static final String CURRENT_USERS = " - В системе работает %d чел";

    private final Pita pita;

    private ButtonBadges buttonAnnouncements;
    private ButtonBadges buttonMail;
    private JSeparator vertSeparator;

    private Button buttonExit;
    private Button buttonSessions;

    private JLabel labelSGO;
    private Button buttonSchoolCard;

    private Button buttonMainPage;
    private Button buttonReportsPage;
    private Button buttonDiaryPage;

    public Header(String schoolName) {
        this.setBackground(new Color(255, 255, 255));

        this.pita = Pita.getPita();

        this.buttonSessions = new Button();
        this.buttonExit = new Button();

        this.labelSGO = new JLabel("Сетевой Город. Образование");
        this.buttonSchoolCard = new Button();
        this.buttonSchoolCard.setText(schoolName);

        this.labelSGO.setFont(new Font("sansserif", Font.BOLD, 12));
        this.labelSGO.setForeground(new Color(127, 127, 127));

        this.buttonSchoolCard.setFont(new Font("sansserif", Font.BOLD, 12));
        this.buttonSchoolCard.setForeground(new Color(127, 127, 127));

        this.vertSeparator = new MySeparator();
        this.buttonAnnouncements = new ButtonBadges();
        this.buttonMail = new ButtonBadges();

        this.buttonMainPage = new Button();
        this.buttonMainPage.setText("Главная");
        this.buttonMainPage.setBackground(new Color(200, 200, 200));
        this.buttonMainPage.setForeground(new Color(127, 127, 127));

        this.buttonReportsPage = new Button();
        this.buttonReportsPage.setText("Отчеты");
        this.buttonReportsPage.setForeground(new Color(127, 127, 127));

        this.buttonDiaryPage = new Button();
        this.buttonDiaryPage.setText("Дневник");
        this.buttonDiaryPage.setForeground(new Color(127, 127, 127));

        this.buttonSessions.setFont(new Font("sansserif", Font.BOLD, 12));
        this.buttonSessions.setForeground(new Color(127, 127, 127));
        try {
            this.buttonSessions.setText(String.format(Utils.getTodaysDateRussian() + Header.CURRENT_USERS, this.pita.getAPI().getActiveSessions().size()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.buttonExit.setForeground(new Color(127, 127, 127));
        this.buttonExit.addActionListener(e -> {
            Pita.getPita().getAPI().logout();
            Pita.getPita().getView().showLoginPanel();
        });
        this.buttonExit.setText("Выход");

        this.vertSeparator.setOrientation(SwingConstants.VERTICAL);

        JSeparator horizSeparator = new JSeparator(SwingConstants.HORIZONTAL);

        this.buttonAnnouncements.setForeground(new Color(250, 49, 49));
        this.buttonAnnouncements.setIcon(Utils.loadIcon("/notification.png"));

        this.buttonMail.setForeground(new Color(63, 178, 232));
        this.buttonMail.setIcon(Utils.loadIcon("/message.png"));
        this.buttonMail.setBadges(5);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(this.labelSGO, GroupLayout.Alignment.LEADING)
                                        .addComponent(this.buttonSchoolCard, GroupLayout.Alignment.LEADING))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 362, Short.MAX_VALUE)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(this.buttonAnnouncements, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                                        .addGap(3, 3, 3)
                                        .addComponent(this.buttonMail, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(this.vertSeparator, GroupLayout.PREFERRED_SIZE, 8, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(this.buttonSessions, GroupLayout.Alignment.TRAILING)
                                        .addComponent(this.buttonExit, GroupLayout.Alignment.TRAILING))
                                .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(horizSeparator)
                                .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(this.buttonMainPage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(this.buttonReportsPage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(this.buttonDiaryPage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(this.labelSGO)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(this.buttonSchoolCard))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(this.buttonSessions)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(this.buttonExit))
                                        .addComponent(this.vertSeparator)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                                .addComponent(this.buttonAnnouncements, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(this.buttonMail, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(horizSeparator)
                                        ))
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(this.buttonMainPage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(this.buttonReportsPage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(this.buttonDiaryPage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        )
        );
    }

    public void setCurrentSessionsNumber(int n) {
        this.buttonSessions.setText(String.format(Utils.getTodaysDateRussian() + Header.CURRENT_USERS, n));
    }
}
