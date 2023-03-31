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

import me.theentropyshard.pita.Pita;
import me.theentropyshard.pita.netschoolapi.NetSchoolAPI;
import me.theentropyshard.pita.netschoolapi.diary.models.Announcement;
import me.theentropyshard.pita.netschoolapi.diary.models.Attachment;
import me.theentropyshard.pita.view.component.GradientLabel;
import me.theentropyshard.pita.view.component.PScrollBar;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AnnouncementsPanel extends JPanel {
    private static final DateTimeFormatter TO_POST_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private final JScrollPane scrollPane;
    private final JPanel panel;

    private int numAnnouncements;

    public AnnouncementsPanel() {
        this.scrollPane = new JScrollPane();
        this.panel = new JPanel();
        this.panel.setBackground(Color.WHITE);

        this.panel.setLayout(new MigLayout("fillx, flowy", "[fill]"));

        this.scrollPane.setBorder(null);
        this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.scrollPane.setViewportView(this.panel);
        this.scrollPane.setVerticalScrollBar(new PScrollBar());

        this.setLayout(new BorderLayout());
        this.add(this.scrollPane, BorderLayout.CENTER);

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
    }

    public void addNewAnnouncement(Announcement a) {
        Font labelFont = new Font("JetBrains Mono", Font.BOLD, 14);
        Font textPaneFont = new Font("JetBrains Mono", Font.PLAIN, 14);

        JPanel container = new JPanel(new MigLayout("nogrid, fillx", "[]", "")) {
            {
                this.setOpaque(false);
                this.addComponentListener(new ComponentAdapter() {
                    @Override
                    public void componentResized(ComponentEvent e) {
                        revalidate();
                    }
                });
                this.setBorder(BorderFactory.createCompoundBorder(this.getBorder(), BorderFactory.createEmptyBorder(0, 5, 5, 5)));
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UIConstants.NEAR_WHITE2);
                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), UIConstants.ARC_WIDTH, UIConstants.ARC_HEIGHT);
                super.paintComponent(g2);
            }
        };

        GradientLabel topicLabel = new GradientLabel(UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
        topicLabel.setText("Тема: " + a.name);
        topicLabel.setFont(labelFont);
        container.add(topicLabel, "grow");

        GradientLabel timeLabel = new GradientLabel(UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
        timeLabel.setText(LocalDateTime.parse(a.postDate).format(AnnouncementsPanel.TO_POST_TIME_FORMATTER));
        timeLabel.setFont(labelFont);
        container.add(timeLabel, "wrap");

        GradientLabel authorLabel = new GradientLabel(UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
        authorLabel.setText("Автор: " + a.author.nickName);
        authorLabel.setFont(labelFont);
        container.add(authorLabel, "wrap");

        JPanel internalContainer = new JPanel(new MigLayout("nogrid, fillx", "[]", "")) {
            {
                this.setOpaque(false);
                this.addComponentListener(new ComponentAdapter() {
                    @Override
                    public void componentResized(ComponentEvent e) {
                        revalidate();
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), UIConstants.ARC_WIDTH, UIConstants.ARC_HEIGHT);
                super.paintComponent(g2);
            }
        };
        container.add(internalContainer, "grow");

        JTextPane mainTextPane = new JTextPane() {
            {
                this.setOpaque(false);
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 7, 7);
                super.paintComponent(g2);
            }
        };

        mainTextPane.setContentType("text/html");
        String txt = AnnouncementsPanel.fixHTMLEntities(a.description);
        mainTextPane.setText("<html><head><style> a { color: #2a5885; } p { font-family: \"JetBrains Mono\"; } </style></head>" + txt + "</html>");
        mainTextPane.setForeground(new Color(120, 120, 120));
        mainTextPane.setSelectionColor(UIConstants.NEAR_WHITE2);
        mainTextPane.setOpaque(false);
        mainTextPane.setEditable(false);
        mainTextPane.setFont(textPaneFont);
        mainTextPane.setMargin(new Insets(-5, 5, 5, 5));
        mainTextPane.addHyperlinkListener(e -> {
            if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                if(Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().browse(e.getURL().toURI());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        if(a.attachments != null && a.attachments.length != 0) {
            internalContainer.add(mainTextPane, "w 100::98%, grow, wrap");

            JPanel attachedFiles = new JPanel();
            attachedFiles.setOpaque(false);
            attachedFiles.setBorder(
                    BorderFactory.createTitledBorder(
                            BorderFactory.createLineBorder(UIConstants.DARK_GREEN, 1),
                            "Прикрепленные файлы",
                            TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                            new Font("JetBrains Mono", Font.BOLD, 12),
                            UIConstants.DARK_GREEN
                    )
            );
            attachedFiles.setLayout(new BoxLayout(attachedFiles, BoxLayout.PAGE_AXIS));

            for(Attachment attach : a.attachments) {
                GradientLabel label = new GradientLabel(attach.name, UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
                label.setFont(new Font("JetBrains Mono", Font.BOLD, 12));
                label.setBorder(new EmptyBorder(0, 5, 3, 0));
                label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                label.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        NetSchoolAPI.I.downloadAttachment(Pita.getPita().getAttachmentsDir(), attach);
                    }
                });
                attachedFiles.add(label);
            }

            internalContainer.add(attachedFiles, "grow");
        } else {
            internalContainer.add(mainTextPane, "w 100::98%, grow");
        }

        if(this.numAnnouncements == 0) {
            this.panel.add(container, "grow");
        } else {
            this.panel.add(container, "grow, gapy 4 0");
        }

        this.numAnnouncements++;
    }

    public void scrollToTop() {
        SwingUtilities.invokeLater(() -> this.scrollPane.getVerticalScrollBar().setValue(0));
    }

    public void scrollToBottom() {
        SwingUtilities.invokeLater(() -> this.scrollPane.getVerticalScrollBar().setValue(this.scrollPane.getVerticalScrollBar().getMaximum()));
    }

    private static String fixHTMLEntities(String raw) {
        return raw
                .replace("amp;#160", "nbsp")
                .replace("&amp;quot;", "\"")
                .replace("&amp;#171;", "«")
                .replace("&amp;#187;", "»");
    }

    public void loadData() {
        List<Announcement> announcements = new ArrayList<>();
        try {
            announcements.addAll(NetSchoolAPI.I.getAnnouncements(-1));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(Announcement a : announcements) {
            this.addNewAnnouncement(a);
        }
        this.scrollToTop();
    }
}
