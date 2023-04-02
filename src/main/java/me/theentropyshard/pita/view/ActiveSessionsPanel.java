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
import me.theentropyshard.pita.view.component.GradientLabel;
import me.theentropyshard.pita.view.component.PScrollBar;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ActiveSessionsPanel extends JPanel {
    private final InfoPanel sessionsPanel;

    public ActiveSessionsPanel() {
        super(new BorderLayout());

        this.sessionsPanel = new InfoPanel();

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new MigLayout("fillx, flowy", "[fill]"));

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setViewportView(panel);
        scrollPane.setVerticalScrollBar(new PScrollBar());

        this.add(scrollPane, BorderLayout.CENTER);

        InfoPanel headerPanel = new InfoPanel();

        UserInfoElement header = new UserInfoElement();
        header.setNumber("№");
        header.setDisplayName("Имя на экране");
        header.setRoles("Роли");

        headerPanel.addDataPanel(header);

        panel.add(headerPanel);
        panel.add(this.sessionsPanel);
    }

    public void loadData() {
        List<UserSession> activeSessions = new ArrayList<>();
        try {
            activeSessions.addAll(NetSchoolAPI.I.getActiveSessions());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < activeSessions.size(); i++) {
            UserSession session = activeSessions.get(i);
            UserInfoElement element = new UserInfoElement();
            element.setNumber(String.valueOf(i + 1));
            element.setDisplayName(session.nickName);
            element.setRoles(session.roles);
            this.sessionsPanel.addDataPanel(element);
        }
    }

    private static class InfoPanel extends JPanel {
        private final JPanel internalInfoPanel;

        public InfoPanel() {
            this.setLayout(new MigLayout("nogrid, fillx", "[]", ""));
            this.setOpaque(false);
            this.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    revalidate();
                }
            });
            this.setBorder(BorderFactory.createCompoundBorder(this.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));

            this.internalInfoPanel = new JPanel(new MigLayout("fillx, flowy", "[fill]")) {
                {
                    this.setOpaque(false);
                    this.addComponentListener(new ComponentAdapter() {
                        @Override
                        public void componentResized(ComponentEvent e) {
                            revalidate();
                        }
                    });
                }

                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(Color.WHITE);
                    g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), UIConstants.ARC_WIDTH, UIConstants.ARC_HEIGHT);
                    super.paintComponent(g2);
                }
            };
            this.add(this.internalInfoPanel, "grow");
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(UIConstants.NEAR_WHITE2);
            g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), UIConstants.ARC_WIDTH, UIConstants.ARC_HEIGHT);
            super.paintComponent(g2);
        }

        public void addDataPanel(UserInfoElement dataPanel) {
            this.internalInfoPanel.add(dataPanel);
        }
    }

    private static class UserInfoElement extends JPanel {
        private final GradientLabel numberLabel;
        private final GradientLabel displayNameLabel;
        private final GradientLabel rolesLabel;

        public UserInfoElement() {
            this.numberLabel = new GradientLabel("", UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
            this.numberLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));

            this.displayNameLabel = new GradientLabel("", UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
            this.displayNameLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));

            this.rolesLabel = new GradientLabel("", UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
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
