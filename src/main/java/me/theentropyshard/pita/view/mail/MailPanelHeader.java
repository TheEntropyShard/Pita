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

import me.theentropyshard.pita.view.UIConstants;
import me.theentropyshard.pita.view.component.GradientLabel;
import me.theentropyshard.pita.view.component.PComboBox;
import me.theentropyshard.pita.view.component.PTextField;
import me.theentropyshard.pita.view.component.SimpleButton;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MailPanelHeader extends CustomPanel {
    public MailPanelHeader(ActionListener lbc) {
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
        numberField.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
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
        loadButton.addActionListener(lbc);
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