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

import me.theentropyshard.pita.netschoolapi.NetSchoolAPI;
import me.theentropyshard.pita.netschoolapi.models.UserSession;
import me.theentropyshard.pita.view.component.PGradientLabel;
import me.theentropyshard.pita.view.component.PScrollBar;
import net.miginfocom.swing.MigLayout;
import retrofit2.Call;
import retrofit2.Response;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ActiveSessionsPanel extends JPanel {
    private final BorderPanel sessionsPanel;

    private int activeSessions;

    public ActiveSessionsPanel() {
        super(new BorderLayout());

        this.sessionsPanel = new BorderPanel();

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new MigLayout("fillx, flowy", "[fill]"));

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setViewportView(panel);
        scrollPane.setVerticalScrollBar(new PScrollBar());

        this.add(scrollPane, BorderLayout.CENTER);

        BorderPanel headerPanel = new BorderPanel();

        UserInfoElement header = new UserInfoElement();
        header.setNumber("№");
        header.setDisplayName("Имя на экране");
        header.setRoles("Роли");

        headerPanel.addComponent(header);

        panel.add(headerPanel);
        panel.add(this.sessionsPanel);
    }

    public void loadData() {
        Call<UserSession[]> activeSessionsCall = NetSchoolAPI.utilsAPI.getActiveSessions();
        try {
            Response<UserSession[]> response = activeSessionsCall.execute();
            UserSession[] userSessions = response.body();
            if (userSessions == null) {
                return;
            }

            this.activeSessions = userSessions.length;
            for (int i = 0; i < userSessions.length; i++) {
                UserSession session = userSessions[i];
                UserInfoElement element = new UserInfoElement();
                element.setNumber(String.valueOf(i + 1));
                element.setDisplayName(session.nickName);
                element.setRoles(session.roles);
                this.sessionsPanel.addComponent(element);
            }
            this.sessionsPanel.revalidate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getActiveSessions() {
        return this.activeSessions;
    }

    private static class UserInfoElement extends JPanel {
        private final PGradientLabel numberLabel;
        private final PGradientLabel displayNameLabel;
        private final PGradientLabel rolesLabel;

        public UserInfoElement() {
            this.numberLabel = new PGradientLabel();
            this.numberLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));

            this.displayNameLabel = new PGradientLabel();
            this.displayNameLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));

            this.rolesLabel = new PGradientLabel();
            this.rolesLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));

            this.setLayout(new GridLayout(1, 3));
            this.setBackground(Color.WHITE);

            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panel.setBackground(Color.WHITE);
            panel.add(this.numberLabel);
            this.add(panel);

            JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
            panel1.setBackground(Color.WHITE);
            panel1.add(this.displayNameLabel);
            this.add(panel1);

            JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            panel2.setBackground(Color.WHITE);
            panel2.add(this.rolesLabel);
            this.add(panel2);
        }

        public void setNumber(String number) {
            this.numberLabel.setText(number);
        }

        public void setDisplayName(String displayName) {
            this.displayNameLabel.setText(displayName);
        }

        public void setRoles(String roles) {
            this.rolesLabel.setText(roles);
        }
    }
}
