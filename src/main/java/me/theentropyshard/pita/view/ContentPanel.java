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

import me.theentropyshard.netschoolapi.diary.models.Announcement;
import me.theentropyshard.pita.Pita;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ContentPanel extends JPanel {
    private Header header;
    private final JPanel content;

    public ContentPanel() { //TODO implement floating windows using undecorated JDialogs
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(1200, 720));

        this.content = new JPanel();
        this.content.setLayout(new BorderLayout());
        this.add(this.content, BorderLayout.CENTER);
    }

    public void loadComponents() {
        try {
            this.header = new Header(Pita.getPita().getAPI().getSchoolInfo().commonInfo.schoolName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.add(this.header, BorderLayout.NORTH);

        AnnouncementsPanel annPanel = new AnnouncementsPanel();

        try {
            List<Announcement> announcements = Pita.getPita().getAPI().getAnnouncements(-1);
            for(Announcement announcement : announcements) {
                annPanel.addNewAnnouncement(new AnnouncementModel(
                        Color.RED,
                        announcement.name,
                        announcement.postDate,
                        announcement.description
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.content.add(annPanel, BorderLayout.CENTER);

        this.revalidate();
    }

    public Header getHeader() {
        return this.header;
    }

    public JPanel getContent() {
        return this.content;
    }
}
