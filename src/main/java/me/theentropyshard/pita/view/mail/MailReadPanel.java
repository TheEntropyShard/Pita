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

import me.theentropyshard.pita.netschoolapi.mail.models.MailRecord;
import me.theentropyshard.pita.view.component.PScrollBar;
import me.theentropyshard.pita.view.component.SimpleButton;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class MailReadPanel extends JPanel {
    private final MailPanel mailPanel;

    public MailReadPanel(MailPanel mailPanel) {
        super(new BorderLayout());

        this.mailPanel = mailPanel;

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new MigLayout("fillx, flowy", "[fill]"));

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setViewportView(panel);
        scrollPane.setVerticalScrollBar(new PScrollBar());

        this.add(scrollPane, BorderLayout.CENTER);

        InfoPanel buttonsPanel = new InfoPanel();
        buttonsPanel.addDataPanel(new CustomPanel() {{
            this.setBackground(Color.WHITE);
            this.setLayout(new FlowLayout(FlowLayout.LEFT));
            this.add(new SimpleButton("Ответить") {{
                this.setRound(true);
            }});
            this.add(new SimpleButton("Переслать сообщение") {{
                this.setRound(true);
            }});
            this.add(new SimpleButton("Удалить") {{
                this.setRound(true);
            }});
        }});

        panel.add(buttonsPanel);
    }

    public void loadData(int index) {
        MailRecord record = this.mailPanel.getRows()[index];
        System.out.println(record);
    }
}
