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

import javax.swing.*;
import java.awt.*;

public class AnnouncementsPanel extends JPanel {
    private final JPanel root;

    public AnnouncementsPanel() {
        this.setLayout(new BorderLayout());

        this.root = new JPanel();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(this.root, BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane(
                panel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void addNewAnnouncement(String subject, String author, String time, String text) {
        JPanel container = new JPanel();
    }
}
