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

public class MainPanel extends JPanel {
    private final CardLayout contentLayout;
    private final JPanel contentPanel;

    private final AnnouncementsPanel annPanel;
    private final DiaryPanel diaryPanel;

    public MainPanel() {
        this.setLayout(new BorderLayout());

        this.contentLayout = new CardLayout();
        this.contentPanel = new JPanel(this.contentLayout);

        this.annPanel = new AnnouncementsPanel();
        this.contentPanel.add(this.annPanel, AnnouncementsPanel.class.getSimpleName());

        this.diaryPanel = new DiaryPanel();
        this.contentPanel.add(this.diaryPanel, DiaryPanel.class.getSimpleName());

        this.contentLayout.show(this.contentPanel, AnnouncementsPanel.class.getSimpleName());
    }
}
