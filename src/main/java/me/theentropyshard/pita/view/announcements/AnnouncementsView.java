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

package me.theentropyshard.pita.view.announcements;

import me.theentropyshard.pita.view.component.ScrollBar;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class AnnouncementsView extends JPanel {
    private final JScrollPane scrollPane;
    private final JPanel panel;

    public AnnouncementsView() {
        this.scrollPane = new JScrollPane();
        this.panel = new JPanel();
        this.panel.setBackground(Color.WHITE);

        this.panel.setLayout(new MigLayout("fillx, flowy", "[fill]"));

        this.scrollPane.setBorder(null);
        this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.scrollPane.setViewportView(this.panel);
        this.scrollPane.setVerticalScrollBar(new ScrollBar());

        this.setLayout(new BorderLayout());
        this.add(this.scrollPane, BorderLayout.CENTER);
    }

    public JScrollPane getScrollPane() {
        return this.scrollPane;
    }

    public JPanel getPanel() {
        return this.panel;
    }
}
