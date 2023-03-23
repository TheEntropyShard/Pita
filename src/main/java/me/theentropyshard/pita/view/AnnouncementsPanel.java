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
import me.theentropyshard.pita.netschoolapi.diary.models.Announcement;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class AnnouncementsPanel extends JPanel {
    private final JPanel root;
    private final JScrollPane scrollPane;

    public AnnouncementsPanel() {
        this.setLayout(new BorderLayout());

        this.root = new JPanel();
        this.root.setLayout(new GridLayout(0, 1));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(this.root, BorderLayout.CENTER);
        scrollPane = new JScrollPane(
                panel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void addNewAnnouncement(String subject, String author, String time, String text) {
        JPanel container = new JPanel(new BorderLayout()) {
            {
                this.setOpaque(false);
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(this.getBackground());
                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 10, 10);
                super.paintComponent(g2);
            }
        };

        JPanel header = new JPanel(new GridLayout(1, 2)); // TODO
        header.add(new JPanel(new GridLayout(2, 1)) {{
            this.add(new JLabel("Тема: " + subject));
            this.add(new JLabel("Автор: " + author));
        }});
        header.add(new JPanel(new GridLayout(2, 1)) {{
            this.add(new JLabel(time));
        }});

        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setText("" + text + "");

        container.add(header, BorderLayout.NORTH);
        container.add(textPane, BorderLayout.CENTER);

        this.root.add(container);
        this.root.revalidate();

        this.scrollPane.getHorizontalScrollBar().setValue(0);
    }

    public void loadData() {
        try {
            for(Announcement a : NetSchoolAPI.I.getAnnouncements(-1)) {
                this.addNewAnnouncement(
                        a.name,
                        a.author.nickName,
                        a.postDate,
                        a.description
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
