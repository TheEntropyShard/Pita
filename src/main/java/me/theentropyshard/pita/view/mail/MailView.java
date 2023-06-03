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

import me.theentropyshard.pita.view.BorderPanel;
import me.theentropyshard.pita.view.component.PScrollBar;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class MailView extends JPanel {
    private final MailPanelHeader header;
    private final MailListPanel mailListPanel;

    public MailView() {
        super(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new MigLayout("fillx, flowy", "[fill]"));

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setViewportView(panel);
        scrollPane.setVerticalScrollBar(new PScrollBar());
        scrollPane.setHorizontalScrollBar(new PScrollBar() {{
            this.setOrientation(JScrollBar.HORIZONTAL);
        }});

        this.add(scrollPane, BorderLayout.CENTER);

        BorderPanel headerPanel = new BorderPanel();

        this.header = new MailPanelHeader(e -> this.loadData(), this);
        headerPanel.addComponent(this.header);

        this.mailListPanel = new MailListPanel();

        BorderPanel mainContent = new BorderPanel();

        mainContent.addComponent(this.mailListPanel);

        panel.add(headerPanel);
        panel.add(mainContent, "gapy 4 0");
    }

    public void loadData() {
            /*MailSearch mailSearch = null;
            if(this.searchText != null && !this.searchText.isEmpty() && this.searchField != null) {
                mailSearch = MailSearch.of(this.searchField, this.searchText);
            }
            Set<Integer> unreadMessagesIds = NetSchoolAPI_old.I.getUnreadMessagesIds();
            MailResponseEntity mail = NetSchoolAPI_old.I.getMail(this.mailBox, MailHelper.getDefaultFields(), null, mailSearch, this.page, this.pageSize);
            this.totalMessages = mail.totalItems;
            MailRecord[] rows = mail.rows;
            this.rows = rows;*/
    }

    public MailPanelHeader getMailPanelHeader() {
        return this.header;
    }

    public MailListPanel getMailListPanel() {
        return this.mailListPanel;
    }
}
