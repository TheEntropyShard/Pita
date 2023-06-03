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
import me.theentropyshard.pita.view.ReportsView;
import me.theentropyshard.pita.view.StudentView;
import me.theentropyshard.pita.view.announcements.AnnouncementsView;
import me.theentropyshard.pita.view.diary.DiaryView;
import me.theentropyshard.pita.view.mail.MailView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;

public class StudentController {
    private static final Logger LOG = LogManager.getLogger(StudentController.class);

    private final StudentView studentView;

    private final HeaderController headerController;
    private final DiaryController diaryController;
    private final ReportsController reportsController;
    private final MailController mailController;
    private final AnnouncementsController annsController;

    private String lastView;

    public StudentController(StudentView studentView) {
        this.studentView = studentView;

        Header header = studentView.getHeader();
        this.headerController = new HeaderController(header);

        DiaryView diaryView = studentView.getDiaryPanel();
        this.diaryController = new DiaryController(diaryView);

        ReportsView reportsView = studentView.getReportsPanel();
        this.reportsController = new ReportsController(reportsView);

        MailView mailView = studentView.getMailPanel();
        this.mailController = new MailController(mailView);

        AnnouncementsView announcementsView = studentView.getAnnouncementsView();
        this.annsController = new AnnouncementsController(announcementsView, Pita.getPita().getAttachmentsDir());
    }

    public void loadHeader() {
        this.headerController.loadData();
    }

    public void switchView(String name) {
        this.loadHeader();

        if (name.equals(this.lastView)) {
            return;
        }

        CardLayout layout = this.studentView.getContentLayout();
        JPanel panel = this.studentView.getContentPanel();
        switch (name) {
            case "diary":
                layout.show(this.studentView.getContentPanel(), DiaryView.class.getName());
                this.diaryController.loadData();
                break;
            case "reports":
                layout.show(panel, ReportsView.class.getName());
                this.reportsController.loadData();
                break;
            case "mail":
                layout.show(panel, MailView.class.getName());
                this.mailController.restoreMailList();
                this.mailController.loadData();
                break;
            case "announcements":
                layout.show(panel, AnnouncementsView.class.getName());
                this.annsController.loadAnnouncements();
                break;
            default:
                LOG.warn("Unknown view: '{}'", name);
                break;
        }

        this.lastView = name;
    }

    public StudentView getStudentView() {
        return this.studentView;
    }
}
