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

package me.theentropyshard.pita.view.mail;

import me.theentropyshard.pita.netschoolapi.NetSchoolAPI;
import me.theentropyshard.pita.netschoolapi.mail.MailBox;
import me.theentropyshard.pita.netschoolapi.mail.MailHelper;
import me.theentropyshard.pita.netschoolapi.mail.models.Mail;
import me.theentropyshard.pita.netschoolapi.mail.models.MailRecord;
import me.theentropyshard.pita.view.MainPanel;
import me.theentropyshard.pita.view.component.PScrollBar;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class MailPanel extends JPanel {
    private static final DateTimeFormatter SENT_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private final MailListPanel mailListPanel;

    private final MailReadPanel mailReadPanel;
    private final MailWritePanel mailWritePanel;

    private int page = 1;
    private int pageSize = 20;
    private MailBox mailBox = MailBox.BOX_INCOMING;

    private MailRecord[] rows;

    public MailPanel(MainPanel mainPanel) {
        super(new BorderLayout());

        this.mailReadPanel = mainPanel.getMailReadPanel();
        this.mailWritePanel = mainPanel.getMailWritePanel();

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new MigLayout("fillx, flowy", "[fill]"));

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setViewportView(panel);
        scrollPane.setVerticalScrollBar(new PScrollBar());

        this.add(scrollPane, BorderLayout.CENTER);

        InfoPanel headerPanel = new InfoPanel();

        MailPanelHeader header = new MailPanelHeader(e -> this.loadData());
        headerPanel.addDataPanel(header);

        this.mailListPanel = new MailListPanel();
        this.mailListPanel.addNewRecord("№", "Автор", "Тема", "Отправлено", false, true);
        this.mailListPanel.addNewRecord(" ", " ", " ", " ", false, true);

        InfoPanel mainContent = new InfoPanel();

        mainContent.addDataPanel(this.mailListPanel);

        panel.add(headerPanel);
        panel.add(mainContent, "gapy 4 0");
    }

    public void loadData() {
        this.mailListPanel.removeAll();
        this.mailListPanel.addNewRecord("№", "От кого", "Тема", "Отправлено", false, true);
        this.mailListPanel.addNewRecord(" ", " ", " ", " ", false, true);

        // TODO сделать выбор страниц
        // кол-во страниц = (кол-во писем / размер страницы) + кол-во писем % размер страницы == 0 ? 0 : 1
        try {
            Set<Integer> unreadMessagesIds = NetSchoolAPI.I.getUnreadMessagesIds();
            Mail mail = NetSchoolAPI.I.getMail(this.mailBox, MailHelper.getDefaultFields(), null, null, this.page, this.pageSize);
            MailRecord[] rows = mail.rows;
            this.rows = rows;
            for(int i = 0; i < rows.length; i++) {
                MailRecord record = rows[i];
                this.mailListPanel.addNewRecord(
                        String.valueOf(i + 1),
                        record.author,
                        record.subject,
                        LocalDateTime.parse(record.sent).format(MailPanel.SENT_TIME_FORMATTER),
                        !unreadMessagesIds.contains(record.id), false
                );
            }
            this.revalidate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MailListPanel getMailListPanel() {
        return this.mailListPanel;
    }

    public MailRecord[] getRows() {
        return this.rows;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public MailBox getMailBox() {
        return this.mailBox;
    }

    public void setMailBox(MailBox mailBox) {
        this.mailBox = mailBox;
    }
}
