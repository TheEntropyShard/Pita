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

    private Button lbRole;
    private JLabel lbUserName;

    private JLabel lbSGO;
    private Button btnSchoolCard;

    private Button pic;

    private Button glav;
    private Button reps;
    private Button diar;


    public Header() {
        initComponents();
    }

    private void initComponents() {

        pic = new Button();
        pic.setText("Выход");

        lbUserName = new JLabel();
        lbRole = new Button();

        lbSGO = new JLabel("Сетевой Город. Образование");
        btnSchoolCard = new Button();
        btnSchoolCard.setText("IMYA SHKOLI");

        lbSGO.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        lbSGO.setForeground(new java.awt.Color(127, 127, 127));

        btnSchoolCard.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnSchoolCard.setForeground(new java.awt.Color(127, 127, 127));

        jSeparator1 = new MySeparator();
        buttonBadges1 = new ButtonBadges();
        buttonBadges2 = new ButtonBadges();

        glav = new Button();
        glav.setText("Главная");
        glav.setBackground(new Color(200, 200, 200));
        glav.setForeground(new java.awt.Color(127, 127, 127));

        reps = new Button();
        reps.setText("Отчеты");
        reps.setForeground(new java.awt.Color(127, 127, 127));

        diar = new Button();
        diar.setText("Дневник");
        diar.setForeground(new java.awt.Color(127, 127, 127));

        setBackground(new java.awt.Color(255, 255, 255));

        lbUserName.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        lbUserName.setForeground(new java.awt.Color(127, 127, 127));
        lbUserName.setText(Utils.getTodaysDateRussian() + " - В системе работает 100");

        lbRole.setForeground(new java.awt.Color(127, 127, 127));
        lbRole.setText("Выход");

        jSeparator1.setOrientation(SwingConstants.VERTICAL);

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);

        buttonBadges1.setForeground(new java.awt.Color(250, 49, 49));
        buttonBadges1.setIcon(new ImageIcon(getClass().getResource("/notification.png"))); // NOI18N
        buttonBadges1.setBadges(12);

        buttonBadges2.setForeground(new java.awt.Color(63, 178, 232));
        buttonBadges2.setIcon(new ImageIcon(getClass().getResource("/message.png"))); // NOI18N
        buttonBadges2.setBadges(5);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(lbSGO, GroupLayout.Alignment.LEADING)
                                        .addComponent(btnSchoolCard, GroupLayout.Alignment.LEADING))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 362, Short.MAX_VALUE)
                                .addComponent(buttonBadges1, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                                .addGap(3, 3, 3)
                                .addComponent(buttonBadges2, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 8, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(lbUserName, GroupLayout.Alignment.TRAILING)
                                        .addComponent(lbRole, GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(separator)
                                .addContainerGap()
                        )
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(glav, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(reps, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(diar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                        //.addContainerGap()
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(lbSGO)
                                                        //.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btnSchoolCard))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(lbUserName)
                                                        //.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(lbRole))
                                                .addComponent(jSeparator1)
                                                .addComponent(buttonBadges1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(buttonBadges2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(separator)
                                                        ))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(glav, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(reps, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(diar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                /*.addContainerGap()*/)
        );
    }
}
