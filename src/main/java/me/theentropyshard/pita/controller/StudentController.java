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

import me.theentropyshard.pita.Pita;
import me.theentropyshard.pita.view.Header;
import me.theentropyshard.pita.view.StudentView;
import me.theentropyshard.pita.view.announcements.AnnouncementsView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;

public class StudentController {
    private static final Logger LOG = LogManager.getLogger(StudentController.class);

    private final StudentView studentView;

    private final HeaderController headerController;
    private final AnnouncementsController annsController;

    public StudentController(StudentView studentView) {
        this.studentView = studentView;

        Header header = studentView.getHeader();
        this.headerController = new HeaderController(header);

        AnnouncementsView announcementsView = studentView.getAnnouncementsView();
        this.annsController = new AnnouncementsController(announcementsView, Pita.getPita().getAttachmentsDir());
    }

    public void loadHeader() {
        this.headerController.loadData();
    }

    public void switchView(String name) {
        this.loadHeader();

        switch (name) {
            case "announcements":
                CardLayout layout = this.studentView.getContentLayout();
                layout.show(this.studentView.getContentPanel(), AnnouncementsView.class.getName());
                this.annsController.loadAnnouncements();
                break;
            case "diary":
                break;
            default:
                LOG.warn("Unknown view: '{}'", name);
                break;
        }
    }
}
