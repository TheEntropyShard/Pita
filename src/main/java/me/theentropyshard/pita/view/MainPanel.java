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
import me.theentropyshard.pita.view.mail.MailPanel;
import me.theentropyshard.pita.view.mail.MailReadPanel;
import me.theentropyshard.pita.view.mail.MailWritePanel;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    private final Header header;

    private final CardLayout contentLayout;
    private final JPanel contentPanel;

    private final DiaryView diaryView;
    private final ReportsPanel reportsPanel;

    private final MailPanel mailPanel;
    private final MailReadPanel mailReadPanel;
    private final MailWritePanel mailWritePanel;

    private final AnnouncementsView annPanel;

    public MainPanel() {
        this.setLayout(new BorderLayout());

        this.header = new Header();
        this.add(this.header, BorderLayout.NORTH);

        this.contentLayout = new CardLayout();
        this.contentPanel = new JPanel(this.contentLayout);
        this.add(this.contentPanel, BorderLayout.CENTER);

        this.diaryView = new DiaryView();
        this.contentPanel.add(this.diaryView, DiaryView.class.getSimpleName());

        this.reportsPanel = new ReportsPanel();
        this.contentPanel.add(this.reportsPanel, ReportsPanel.class.getSimpleName());

        this.mailPanel = new MailPanel();
        this.contentPanel.add(this.mailPanel, MailPanel.class.getSimpleName());

        this.mailReadPanel = new MailReadPanel(this.mailPanel);
        this.contentPanel.add(this.mailReadPanel, MailReadPanel.class.getSimpleName());

        this.mailWritePanel = new MailWritePanel();
        this.contentPanel.add(this.mailWritePanel, MailWritePanel.class.getSimpleName());

        this.annPanel = new AnnouncementsView();
        this.contentPanel.add(this.annPanel, AnnouncementsView.class.getSimpleName());
    }

    public void showComponents() {
        this.header.loadData();
        this.diaryView.loadData();
        this.contentLayout.show(this.contentPanel, DiaryView.class.getSimpleName());
    }

    public Header getHeader() {
        return this.header;
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

    public ReportsPanel getReportsPanel() {
        return this.reportsPanel;
    }

    public MailPanel getMailPanel() {
        return this.mailPanel;
    }

    public MailReadPanel getMailReadPanel() {
        return this.mailReadPanel;
    }

    public MailWritePanel getMailWritePanel() {
        return this.mailWritePanel;
    }

    public AnnouncementsView getAnnPanel() {
        return this.annPanel;
    }
}
