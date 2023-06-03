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

import me.theentropyshard.pita.view.announcements.AnnouncementsView;
import me.theentropyshard.pita.view.diary.DiaryView;
import me.theentropyshard.pita.view.downloads.DownloadsPanel;
import me.theentropyshard.pita.view.mail.MailView;
import me.theentropyshard.pita.view.mail.MailReadPanel;
import me.theentropyshard.pita.view.mail.MailWritePanel;

import javax.swing.*;
import java.awt.*;

public class StudentView extends JPanel {
    private final Header header;
    private final DownloadsPanel downloadsPanel;

    private final CardLayout contentLayout;
    private final JPanel contentPanel;

    private final DiaryView diaryView;
    private final ReportsView reportsView;

    private final MailView mailView;
    private final MailReadPanel mailReadPanel;
    private final MailWritePanel mailWritePanel;

    private final AnnouncementsView annPanel;

    public StudentView() {
        this.setLayout(new BorderLayout());

        this.header = new Header();
        this.add(this.header, BorderLayout.NORTH);

        this.downloadsPanel = new DownloadsPanel();
        this.downloadsPanel.setVisible(false);
        this.add(this.downloadsPanel, BorderLayout.SOUTH);

        this.contentLayout = new CardLayout();
        this.contentPanel = new JPanel(this.contentLayout);
        this.add(this.contentPanel, BorderLayout.CENTER);

        this.diaryView = new DiaryView();
        this.contentPanel.add(this.diaryView, DiaryView.class.getName());

        this.reportsView = new ReportsView();
        this.contentPanel.add(this.reportsView, ReportsView.class.getName());

        this.mailView = new MailView();
        this.contentPanel.add(this.mailView, MailView.class.getName());

        this.mailReadPanel = new MailReadPanel(this.mailView);
        this.contentPanel.add(this.mailReadPanel, MailReadPanel.class.getName());

        this.mailWritePanel = new MailWritePanel();
        this.contentPanel.add(this.mailWritePanel, MailWritePanel.class.getName());

        this.annPanel = new AnnouncementsView();
        this.contentPanel.add(this.annPanel, AnnouncementsView.class.getName());
    }

    public Header getHeader() {
        return this.header;
    }

    public DownloadsPanel getDownloadsPanel() {
        return this.downloadsPanel;
    }

    public CardLayout getContentLayout() {
        return this.contentLayout;
    }

    public JPanel getContentPanel() {
        return this.contentPanel;
    }

    public DiaryView getDiaryPanel() {
        return this.diaryView;
    }

    public ReportsView getReportsPanel() {
        return this.reportsView;
    }

    public MailView getMailPanel() {
        return this.mailView;
    }

    public MailReadPanel getMailReadPanel() {
        return this.mailReadPanel;
    }

    public MailWritePanel getMailWritePanel() {
        return this.mailWritePanel;
    }

    public AnnouncementsView getAnnouncementsView() {
        return this.annPanel;
    }
}
