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

import me.theentropyshard.pita.Pita;
import me.theentropyshard.pita.netschoolapi.NetSchoolAPI;
import me.theentropyshard.pita.netschoolapi.diary.models.Attachment;
import me.theentropyshard.pita.netschoolapi.mail.models.MailRecord;
import me.theentropyshard.pita.netschoolapi.mail.models.Message;
import me.theentropyshard.pita.netschoolapi.models.UserModel;
import me.theentropyshard.pita.view.AnnouncementsPanel;
import me.theentropyshard.pita.view.UIConstants;
import me.theentropyshard.pita.view.component.GradientLabel;
import me.theentropyshard.pita.view.component.PScrollBar;
import me.theentropyshard.pita.view.component.SimpleButton;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

public class MailReadPanel extends JPanel {
    private static final DateTimeFormatter SENT_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private final MailPanel mailPanel;

    private final DataElementPanel from;
    private final DataElementPanel to;
    private final DataElementPanel copy;
    private final DataElementPanel sent;
    private final DataElementPanel subject;

    private final InfoPanel mailBodyPanel;

    public MailReadPanel(MailPanel mailPanel) {
        super(new BorderLayout());

        this.mailPanel = mailPanel;

        this.from = new DataElementPanel();
        this.from.setKey("От кого");

        this.to = new DataElementPanel();
        this.to.setKey("Кому");

        this.copy = new DataElementPanel();
        this.copy.setKey("Копия");

        this.sent = new DataElementPanel();
        this.sent.setKey("Отправлено");

        this.subject = new DataElementPanel();
        this.subject.setKey("Тема");

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

        InfoPanel mailHeadersPanel = new InfoPanel();
        mailHeadersPanel.addDataPanel(this.from, "growx, width 0:0:100%");
        mailHeadersPanel.addDataPanel(this.to, "growx, width 0:0:100%");
        mailHeadersPanel.addDataPanel(this.copy, "growx, width 0:0:100%");
        mailHeadersPanel.addDataPanel(this.sent, "growx, width 0:0:100%");
        mailHeadersPanel.addDataPanel(this.subject, "growx, width 0:0:100%");

        this.mailBodyPanel = new InfoPanel();
        this.mailBodyPanel.getInternalInfoPanel().setLayout(new MigLayout("nogrid, fillx", "[]", ""));

        panel.add(buttonsPanel);
        panel.add(mailHeadersPanel);
        panel.add(this.mailBodyPanel);
    }

    public void loadData(int index) {
        MailRecord mailRecord = this.mailPanel.getRows()[index];
        try {
            Message message = NetSchoolAPI.I.readMessage(mailRecord.id);
            StringJoiner joiner = new StringJoiner("; ");
            for(UserModel model : message.to) {
                joiner.add(model.name);
            }

            this.from.setValue(message.author.name);
            this.to.setValue(joiner.toString());
            this.copy.setValue("");
            this.sent.setValue(LocalDateTime.parse(message.sent).format(MailReadPanel.SENT_TIME_FORMATTER));
            this.subject.setValue(message.subject);

            this.mailBodyPanel.removeAll();

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

            Font textPaneFont = new Font("JetBrains Mono", Font.PLAIN, 14);
            mainTextPane.setFont(textPaneFont);
            mainTextPane.setContentType("text/html");
            String txt = AnnouncementsPanel.fixHTMLEntities(message.text);
            mainTextPane.setText("<html><head><style> a { color: #2a5885; } p { font-family: \"JetBrains Mono\"; } </style></head><p>" + txt + "</p></html>");
            mainTextPane.setForeground(new Color(120, 120, 120));
            mainTextPane.setSelectionColor(UIConstants.NEAR_WHITE2);
            mainTextPane.setOpaque(false);
            mainTextPane.setEditable(false);
            mainTextPane.setMargin(new Insets(-10, 5, 5, 5));
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

            if(message.fileAttachments != null && message.fileAttachments.length != 0) {
                this.mailBodyPanel.addDataPanel(mainTextPane, "w 100::98%, grow, wrap");

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

                for(Attachment attach : message.fileAttachments) {
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

                this.mailBodyPanel.addDataPanel(attachedFiles, "grow");
            } else {
                this.mailBodyPanel.addDataPanel(mainTextPane, "w 100::98%, grow");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class DataElementPanel extends CustomPanel {
        private final GradientLabel keyLabel;
        private final GradientLabel valueLabel;

        public DataElementPanel() {
            this.keyLabel = new GradientLabel("", UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
            this.keyLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));

            this.valueLabel = new GradientLabel("", UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN) {
                @Override
                protected void paintComponent(Graphics g) {
                    Color oldColor = g.getColor();
                    g.setColor(new Color(240, 240, 240));
                    g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 7, 7);
                    g.setColor(oldColor);
                    super.paintComponent(g);
                }
            };
            this.valueLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
            this.valueLabel.setBorder(new EmptyBorder(5, 5, 5, 5));

            this.setLayout(new GridLayout(1, 1));
            this.setBackground(Color.WHITE);

            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panel.setBackground(Color.WHITE);
            panel.add(this.keyLabel);
            this.add(panel);
            this.add(this.valueLabel);
        }

        public void setKey(String key) {
            this.keyLabel.setText(key);
        }

        public void setValue(String value) {
            this.valueLabel.setText(value);
        }
    }
}
