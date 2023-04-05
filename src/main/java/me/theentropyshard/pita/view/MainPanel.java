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

import me.theentropyshard.pita.view.mail.MailPanel;
import me.theentropyshard.pita.view.mail.MailReadPanel;
import me.theentropyshard.pita.view.mail.MailWritePanel;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    private final Header header;

    private final CardLayout contentLayout;
    private final JPanel contentPanel;

    private final DiaryPanel diaryPanel;
    private final ReportsPanel reportsPanel;

    private final MailPanel mailPanel;
    private final MailReadPanel mailReadPanel;
    private final MailWritePanel mailWritePanel;

    private final AnnouncementsPanel annPanel;

    public MainPanel() {
        this.setLayout(new BorderLayout());

        this.header = new Header();
        this.add(this.header, BorderLayout.NORTH);

        this.contentLayout = new CardLayout();
        this.contentPanel = new JPanel(this.contentLayout);
        this.add(this.contentPanel, BorderLayout.CENTER);

        this.diaryPanel = new DiaryPanel();
        this.contentPanel.add(this.diaryPanel, DiaryPanel.class.getSimpleName());

        this.reportsPanel = new ReportsPanel();
        this.contentPanel.add(this.reportsPanel, ReportsPanel.class.getSimpleName());

        this.mailPanel = new MailPanel(this);
        this.contentPanel.add(this.mailPanel, MailPanel.class.getSimpleName());

        this.mailReadPanel = new MailReadPanel(this.mailPanel);
        this.contentPanel.add(this.mailReadPanel, MailReadPanel.class.getSimpleName());

        this.mailWritePanel = new MailWritePanel();
        this.contentPanel.add(this.mailWritePanel, MailWritePanel.class.getSimpleName());

        this.annPanel = new AnnouncementsPanel();
        this.contentPanel.add(this.annPanel, AnnouncementsPanel.class.getSimpleName());
    }

    public void showComponents() {
        this.header.loadData();
        this.diaryPanel.loadData();
        this.contentLayout.show(this.contentPanel, DiaryPanel.class.getSimpleName());
    }

    public CardLayout getContentLayout() {
        return this.contentLayout;
    }

    public JPanel getContentPanel() {
        return this.contentPanel;
    }

    public DiaryPanel getDiaryPanel() {
        return this.diaryPanel;
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

    public AnnouncementsPanel getAnnPanel() {
        return this.annPanel;
    }
}
