/*      NetSchoolAPI. A simple API client for NetSchool by irTech
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

import javax.swing.*;
import java.awt.*;

public class Header extends JPanel {
    private ButtonBadges buttonBadges1;
    private ButtonBadges buttonBadges2;
    private JSeparator jSeparator1;

    private Button buttonExit;
    private Button buttonSessions;

    private JLabel labelSGO;
    private Button buttonSchoolCard;

    private Button buttonMainPage;
    private Button buttonReportsPage;
    private Button buttonDiaryPage;

    public Header(String schoolName) {
        buttonSessions = new Button();
        buttonExit = new Button();

        labelSGO = new JLabel("Сетевой Город. Образование");
        buttonSchoolCard = new Button();
        buttonSchoolCard.setText(schoolName);

        labelSGO.setFont(new Font("sansserif", 1, 12));
        labelSGO.setForeground(new Color(127, 127, 127));

        buttonSchoolCard.setFont(new Font("sansserif", 1, 12));
        buttonSchoolCard.setForeground(new Color(127, 127, 127));

        jSeparator1 = new MySeparator();
        buttonBadges1 = new ButtonBadges();
        buttonBadges2 = new ButtonBadges();

        buttonMainPage = new Button();
        buttonMainPage.setText("Главная");
        buttonMainPage.setBackground(new Color(200, 200, 200));
        buttonMainPage.setForeground(new Color(127, 127, 127));

        buttonReportsPage = new Button();
        buttonReportsPage.setText("Отчеты");
        buttonReportsPage.setForeground(new Color(127, 127, 127));

        buttonDiaryPage = new Button();
        buttonDiaryPage.setText("Дневник");
        buttonDiaryPage.setForeground(new Color(127, 127, 127));

        setBackground(new Color(255, 255, 255));

        buttonSessions.setFont(new Font("sansserif", 1, 12));
        buttonSessions.setForeground(new Color(127, 127, 127));
        buttonSessions.setText(Utils.getTodaysDateRussian() + " - В системе работает 100");

        buttonExit.setForeground(new Color(127, 127, 127));
        buttonExit.setText("Выход");

        jSeparator1.setOrientation(SwingConstants.VERTICAL);

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);

        buttonBadges1.setForeground(new Color(250, 49, 49));
        buttonBadges1.setIcon(Utils.loadIcon("/notification.png"));

        buttonBadges2.setForeground(new Color(63, 178, 232));
        buttonBadges2.setIcon(Utils.loadIcon("/message.png"));
        buttonBadges2.setBadges(5);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(labelSGO, GroupLayout.Alignment.LEADING)
                                        .addComponent(buttonSchoolCard, GroupLayout.Alignment.LEADING))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 362, Short.MAX_VALUE)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(buttonBadges1, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                                        .addGap(3, 3, 3)
                                        .addComponent(buttonBadges2, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 8, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(buttonSessions, GroupLayout.Alignment.TRAILING)
                                        .addComponent(buttonExit, GroupLayout.Alignment.TRAILING))
                                .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(separator)
                                .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(buttonMainPage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buttonReportsPage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buttonDiaryPage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(labelSGO)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(buttonSchoolCard))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(buttonSessions)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(buttonExit))
                                        .addComponent(jSeparator1)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                                .addComponent(buttonBadges1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(buttonBadges2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(separator)
                                        ))
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(buttonMainPage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(buttonReportsPage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(buttonDiaryPage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        )
        );
    }
}
