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

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URISyntaxException;

public class AnnouncementsPanel extends JPanel {
    private final JScrollPane scrollPane;
    private final JPanel panel;
    
    public AnnouncementsPanel() {
        this.setBackground(Color.WHITE);

        this.scrollPane = new JScrollPane();
        this.panel = new JPanel();

        this.scrollPane.setBorder(null);
        this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        this.panel.setBackground(new Color(255, 255, 255));

        GroupLayout panelLayout = new GroupLayout(this.panel);
        this.panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
                panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 329, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
                panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 351, Short.MAX_VALUE)
        );

        this.scrollPane.setViewportView(this.panel);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(this.scrollPane)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(this.scrollPane, GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
        );

        Action actionScrollTop = new AbstractAction("SCROLL_TO_TOP") {
            @Override
            public void actionPerformed(ActionEvent e) {
                scrollToTop();
            }
        };

        Action actionScrollDown = new AbstractAction("SCROLL_TO_BOTTOM") {
            @Override
            public void actionPerformed(ActionEvent e) {
                scrollToBottom();
            }
        };

        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_HOME, 0), "SCROLL_TO_TOP");
        this.getActionMap().put("SCROLL_TO_TOP", actionScrollTop);

        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_END, 0), "SCROLL_TO_BOTTOM");
        this.getActionMap().put("SCROLL_TO_BOTTOM", actionScrollDown);

        this.scrollPane.setVerticalScrollBar(new ScrollBarCustom());
        this.panel.setLayout(new MigLayout("nogrid, flowy, alignx"));
    }

    public void addNewAnnouncement(AnnouncementModel model) {
        JPanel container = new JPanel(new MigLayout("nogrid, fillx"));
        container.setBorder(new LineBorder(Color.RED, 1));

        JLabel topic = new JLabel("<html><span style=\"color:rgb(102, 102, 102);\">Тема:</span><span style=\"color:#06A;\"> " + model.getTitle() + "</span></html>");
        topic.setFont(new Font("sansserif", Font.BOLD, 12));
        topic.setForeground(model.getTitleColor());
        container.add(topic);
        JLabel time = new JLabel("<html><span style=\"color:rgb(102, 102, 102);\">" + model.getTime() + "</span><html>");
        time.setForeground(new Color(180, 180, 180));
        container.add(time, "wrap");
        JTextPane txt = new JTextPane();
        txt.setBackground(new Color(0, 0, 0, 0));
        txt.setForeground(new Color(120, 120, 120));
        txt.setSelectionColor(new Color(150, 150, 150));
        txt.setBorder(null);
        txt.setOpaque(false);
        txt.setEditable(false);
        txt.setContentType("text/html");
        txt.addHyperlinkListener(e -> {
            if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                if(Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().browse(e.getURL().toURI());
                    } catch (IOException | URISyntaxException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        String text = model.getDescription().replace("amp;#160", "nbsp");
        txt.setText("<html style=\"font-family: Arial, Helvetica, sans-serif;\">" + text + "</html>");
        container.add(txt, "w 100::90%, wrap");
        Dimension preferredSize = container.getPreferredSize();
        container.setPreferredSize(new Dimension(800, preferredSize.height));
        this.panel.add(container, "w 75%");
    } // TODO add author information next to the text block

    public void scrollToTop() {
        SwingUtilities.invokeLater(() -> this.scrollPane.getVerticalScrollBar().setValue(0));
    }

    public void scrollToBottom() {
        SwingUtilities.invokeLater(() -> this.scrollPane.getVerticalScrollBar().setValue(this.scrollPane.getVerticalScrollBar().getMaximum()));
    }
}
