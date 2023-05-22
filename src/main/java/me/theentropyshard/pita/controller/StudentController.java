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

package me.theentropyshard.pita.controller;

import me.theentropyshard.pita.Config;
import me.theentropyshard.pita.view.StudentView;
import me.theentropyshard.pita.view.announcements.AnnouncementsView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;

public class StudentController {
    private static final Logger LOG = LogManager.getLogger(StudentController.class);

    private final StudentView studentView;

    private final AnnouncementsController announcementsController;

    public StudentController(StudentView studentView) {
        this.studentView = studentView;

        AnnouncementsView announcementsView = studentView.getAnnouncementsView();
        this.announcementsController = new AnnouncementsController(announcementsView);
    }

    public void showPreferredView() {
        System.out.println("in showPreferredView");

        String preferredView = Config.getString("preferredView", "announcements");
        switch (preferredView) {
            case "announcements":
                CardLayout layout = this.studentView.getContentLayout();
                layout.show(this.studentView.getContentPanel(), AnnouncementsView.class.getName());
                this.announcementsController.loadAnnouncements();
                break;
            default:
                LOG.warn("Unknown view: '{}'", preferredView);
                break;
        }
    }
}
