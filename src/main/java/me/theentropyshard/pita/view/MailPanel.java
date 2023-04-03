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

import me.theentropyshard.pita.netschoolapi.NetSchoolAPI;
import me.theentropyshard.pita.netschoolapi.mail.MailBox;
import me.theentropyshard.pita.netschoolapi.mail.MailHelper;
import me.theentropyshard.pita.netschoolapi.mail.models.Mail;
import me.theentropyshard.pita.netschoolapi.mail.models.MailRecord;
import me.theentropyshard.pita.view.component.*;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MailPanel extends JPanel {
    private static final DateTimeFormatter SENT_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private final MailContentPanel mailContentPanel;

    public MailPanel() {
        super(new BorderLayout());

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

        MailPanelHeader header = new MailPanelHeader();
        headerPanel.addDataPanel(header);

        this.mailContentPanel = new MailContentPanel();
        this.mailContentPanel.addNewRecord("№", "Автор", "Тема", "Отправлено");

        InfoPanel mainContent = new InfoPanel();

        mainContent.addDataPanel(this.mailContentPanel);

        panel.add(headerPanel);
        panel.add(mainContent);
    }

    public void loadData() {
        try {
            Mail mail = NetSchoolAPI.I.getMail(MailBox.BOX_INCOMING, MailHelper.getDefaultFields(), null, null, 1, 20);
            MailRecord[] rows = mail.rows;
            for(int i = 0; i < rows.length; i++) {
                MailRecord record = rows[i];
                this.mailContentPanel.addNewRecord(
                        String.valueOf(i + 1),
                        record.author,
                        record.subject,
                        LocalDateTime.parse(record.sent).format(MailPanel.SENT_TIME_FORMATTER)
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class InfoPanel extends JPanel { //TODO попытаться сделать эти классы более generic, а не копипастить их
        private final JPanel internalInfoPanel;

        public InfoPanel() {
            this.setLayout(new MigLayout("nogrid, fillx", "[]", ""));
            this.setOpaque(false);
            this.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    revalidate();
                }
            });
            this.setBorder(BorderFactory.createCompoundBorder(this.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));

            this.internalInfoPanel = new JPanel(new MigLayout("fillx, flowy", "[fill]")) {
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
            this.add(this.internalInfoPanel, "grow");
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(UIConstants.NEAR_WHITE2);
            g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), UIConstants.ARC_WIDTH, UIConstants.ARC_HEIGHT);
            super.paintComponent(g2);
        }

        public void addDataPanel(CustomPanel customPanel) {
            this.internalInfoPanel.add(customPanel);
        }
    }

    public static class CustomPanel extends JPanel {

    }

    private static class MailPanelHeader extends CustomPanel {
        public MailPanelHeader() {
            this.setLayout(new MigLayout("flowy", "[left]15[left]5[left]15[left]push", "[center][center][center]"));
            this.setBackground(Color.WHITE);

            GradientLabel label = new GradientLabel("Почтовая папка", UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
            label.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
            this.add(label, "cell 0 0");

            PComboBox comboBox = new PComboBox();
            comboBox.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
            comboBox.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 5));
            comboBox.addItem("Входящие");
            comboBox.addItem("Отправленные");
            comboBox.addItem("Удаленные");
            comboBox.addItem("Черновики");

            this.add(comboBox, "cell 0 1");

            GradientLabel searchLabel = new GradientLabel("Поиск", UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
            searchLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
            this.add(searchLabel, "cell 1 0");

            PComboBox searchCB = new PComboBox();
            searchCB.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
            searchCB.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 5));
            searchCB.addItem("От кого");
            searchCB.addItem("Кому");
            searchCB.addItem("Тема");

            PTextField searchField = new PTextField();
            searchField.setHint("Введите текст...");
            searchField.setFont(new Font("JetBrains Mono", Font.BOLD, 14));

            Dimension preferredSize = searchField.getPreferredSize();
            searchField.setPreferredSize(new Dimension(250, preferredSize.height));

            comboBox.setPreferredSize(new Dimension(comboBox.getPreferredSize().width, preferredSize.height));
            searchCB.setPreferredSize(new Dimension(searchCB.getPreferredSize().width, preferredSize.height));

            this.add(searchCB, "cell 1 1");
            this.add(searchField, "cell 2 1");

            searchCB.setPreferredSize(comboBox.getPreferredSize());

            GradientLabel numberLabel = new GradientLabel("Число записей на странице", UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
            numberLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));

            this.add(numberLabel, "cell 3 0");

            PTextField numberField = new PTextField();
            numberField.setPreferredSize(new Dimension(250, searchField.getPreferredSize().height));
            numberField.setText("20");

            // See https://stackoverflow.com/a/11093360/19857533
            ((PlainDocument) numberField.getDocument()).setDocumentFilter(new DocumentFilter() {
                @Override
                public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                    Document doc = fb.getDocument();
                    StringBuilder sb = new StringBuilder();
                    sb.append(doc.getText(0, doc.getLength()));
                    sb.insert(offset, string);

                    if(this.test(sb.toString())) {
                        super.insertString(fb, offset, string, attr);
                    }
                }

                private boolean test(String text) {
                    try {
                        Integer.parseInt(text);
                    } catch (NumberFormatException e) {
                        return false;
                    }
                    return true;
                }

                @Override
                public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                    Document doc = fb.getDocument();
                    StringBuilder sb = new StringBuilder();
                    sb.append(doc.getText(0, doc.getLength()));
                    sb.replace(offset, offset + length, text);

                    if(this.test(sb.toString())) {
                        super.replace(fb, offset, length, text, attrs);
                    }
                }

                @Override
                public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
                    Document doc = fb.getDocument();
                    StringBuilder sb = new StringBuilder();
                    sb.append(doc.getText(0, doc.getLength()));
                    sb.delete(offset, offset + length);

                    if(sb.toString().length() == 0) {
                        super.replace(fb, offset, length, "20", null);
                    } else {
                        if(this.test(sb.toString())) {
                            super.remove(fb, offset, length);
                        }
                    }
                }
            });

            this.add(numberField, "cell 3 1");

            JPanel panel = new JPanel(new MigLayout("insets 0, fillx"));
            panel.setBackground(Color.WHITE);

            SimpleButton loadButton = new SimpleButton("Загрузить");
            loadButton.setRound(true);

            panel.add(loadButton, "");

            SimpleButton writeButton = new SimpleButton("Написать");
            writeButton.setRound(true);

            panel.add(writeButton, "");

            SimpleButton deleteButton = new SimpleButton("Удалить");
            deleteButton.setRound(true);

            panel.add(deleteButton, "");

            this.add(panel, "cell 0 2, span");
        }
    }

    public static class MailContentPanel extends CustomPanel {
        public MailContentPanel() {
            this.setLayout(new MigLayout("", "[left][center, grow][center, grow][center]", "[center, fill]"));
            this.setBackground(Color.WHITE);
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e);
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            });
        }

        public void addNewRecord(String number, String author, String subject, String sent) {
            GradientLabel numberLabel = new GradientLabel(number, UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
            numberLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));

            GradientLabel authorLabel = new GradientLabel(author, UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
            authorLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));

            GradientLabel subjectLabel = new GradientLabel(subject, UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
            subjectLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));

            GradientLabel sentLabel = new GradientLabel(sent, UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
            sentLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));

            this.add(numberLabel);
            this.add(authorLabel);
            this.add(subjectLabel);
            this.add(sentLabel, "wrap");
        }
    }
}
