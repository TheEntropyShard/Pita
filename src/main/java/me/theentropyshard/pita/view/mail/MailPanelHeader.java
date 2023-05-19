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
import me.theentropyshard.pita.netschoolapi.mail.MailField;
import me.theentropyshard.pita.netschoolapi.mail.models.MailRecord;
import me.theentropyshard.pita.view.*;
import me.theentropyshard.pita.view.component.GradientLabel;
import me.theentropyshard.pita.view.component.ComboBox;
import me.theentropyshard.pita.view.component.TextField;
import me.theentropyshard.pita.view.component.SimpleButton;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;

public class MailPanelHeader extends JPanel {
    private final GradientLabel pageLabel;
    private final TextField pageField;

    public MailPanelHeader(ActionListener lbc, MailPanel mailPanel) {
        this.setLayout(new MigLayout("flowy", "[left]15[left]5[left]15[left]15[left]push", "[center][center][center]"));
        this.setBackground(Color.WHITE);

        GradientLabel label = new GradientLabel("Почтовая папка");
        label.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
        this.add(label, "cell 0 0");

        ComboBox comboBox = new ComboBox();
        comboBox.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
        comboBox.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 5));
        comboBox.addItem("Входящие");
        comboBox.addItem("Отправленные");
        comboBox.addItem("Удаленные");
        comboBox.addItem("Черновики");

        comboBox.addItemListener(e -> {
            String item = (String) comboBox.getSelectedItem();
            switch(Objects.requireNonNull(item)) {
                case "Входящие":
                    mailPanel.setMailBox(MailBox.BOX_INCOMING);
                    break;
                case "Отправленные":
                    mailPanel.setMailBox(MailBox.BOX_SENT);
                    break;
                case "Удаленные":
                    mailPanel.setMailBox(MailBox.BOX_DELETED);
                    break;
                case "Черновики":
                    mailPanel.setMailBox(MailBox.BOX_DRAFTS);
                    break;
                default:
                    throw new RuntimeException("Unreachable: " + item);
            }
            mailPanel.loadData();
        });

        this.add(comboBox, "cell 0 1");

        GradientLabel searchLabel = new GradientLabel("Поиск");
        searchLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
        this.add(searchLabel, "cell 1 0");

        ComboBox searchCB = new ComboBox();
        searchCB.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
        searchCB.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 5));
        searchCB.addItem("От кого");
        searchCB.addItem("Кому");
        searchCB.addItem("Тема");

        searchCB.addItemListener(e -> {
            String item = (String) searchCB.getSelectedItem();
            switch(Objects.requireNonNull(item)) {
                case "От кого":
                    mailPanel.setSearchField(MailField.AUTHOR);
                    break;
                case "Кому":
                    mailPanel.setSearchField(MailField.TO_NAMES);
                    break;
                case "Тема":
                    mailPanel.setSearchField(MailField.SUBJECT);
                    break;
                default:
                    throw new RuntimeException("Unreachable: " + item);
            }
        });

        TextField searchField = new TextField();
        searchField.setHint("Введите текст...");
        searchField.setFont(new Font("JetBrains Mono", Font.BOLD, 14));

        Dimension preferredSize = searchField.getPreferredSize();
        searchField.setPreferredSize(new Dimension(250, preferredSize.height));

        comboBox.setPreferredSize(new Dimension(comboBox.getPreferredSize().width, preferredSize.height));
        searchCB.setPreferredSize(new Dimension(searchCB.getPreferredSize().width, preferredSize.height));

        this.add(searchCB, "cell 1 1");
        this.add(searchField, "cell 2 1");

        searchCB.setPreferredSize(comboBox.getPreferredSize());

        GradientLabel numberLabel = new GradientLabel("Число записей на странице");
        numberLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));

        this.add(numberLabel, "cell 3 0");

        TextField numberField = new TextField();
        numberField.setPreferredSize(new Dimension(250, searchField.getPreferredSize().height));
        numberField.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
        numberField.setText("20");

        this.add(numberField, "cell 3 1");

        this.pageLabel = new GradientLabel("");
        this.pageLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));

        this.add(this.pageLabel, "cell 4 0");

        this.pageField = new TextField();
        this.pageField.setText("1");
        this.pageField.setPreferredSize(new Dimension(250, searchField.getPreferredSize().height));
        this.pageField.setFont(new Font("JetBrains Mono", Font.BOLD, 14));

        this.add(this.pageField, "cell 4 1");

        JPanel panel = new JPanel(new MigLayout("insets 0, fillx"));
        panel.setBackground(Color.WHITE);

        SimpleButton loadButton = new SimpleButton("Загрузить");
        loadButton.addActionListener(e -> {
            int pageSize = 20;
            try {
                pageSize = Integer.parseInt(numberField.getText());
            } catch (NumberFormatException ignored) {

            }

            mailPanel.setPageSize(pageSize);
            mailPanel.setSearchText(searchField.getText());
            int page = 1;
            try {
                page = Integer.parseInt(this.pageField.getText());
            } catch (NumberFormatException ignored) {

            }
            mailPanel.setPage(page);
            lbc.actionPerformed(e);
        });
        loadButton.setRoundCorners(true);

        panel.add(loadButton, "");

        SimpleButton writeButton = new SimpleButton("Написать");
        writeButton.setRoundCorners(true);
        writeButton.addActionListener(e -> {
            View v = View.getView();
            MainPanel mp = v.getMainPanel();
            mp.getContentLayout().show(mp.getContentPanel(), MailWritePanel.class.getSimpleName());
        });

        panel.add(writeButton, "");

        SimpleButton deleteButton = new SimpleButton("Удалить");
        deleteButton.setRoundCorners(true);
        deleteButton.addActionListener(e -> {
            Set<String> selectedRows = mailPanel.getMailListPanel().getSelectedRows();
            MailRecord[] rows = mailPanel.getRows();

            int[] messageIds = new int[selectedRows.size()];

            int i = 0;
            for(String s : selectedRows) {
                messageIds[i] = rows[Integer.parseInt(s) - 1].id;
                i++;
            }

            if(selectedRows.size() != 0) {
                View.getView().getFrame().getGlassPane().setVisible(true);

                MessageDialog dialog = new MessageDialog("Подтверждение", "Вы хотите выбранные письма в папку \"Удаленные\"?", true);

                View.getView().getFrame().getGlassPane().setVisible(false);

                if(dialog.getResult() == MessageDialog.Result.OK) {
                    View.getView().getFrame().getGlassPane().setVisible(true);

                    boolean success = true;

                    try {
                        NetSchoolAPI.I.deleteMessages(false, messageIds);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        success = false;
                    }

                    if(success) {
                        new MessageDialog("Внимание", "Ваши письма помещены в папку \"Удаленные\"", true);
                        MainPanel mainPanel = View.getView().getMainPanel();
                        mainPanel.getMailPanel().loadData();
                        mainPanel.getContentLayout().show(mainPanel.getContentPanel(), MailPanel.class.getSimpleName());
                    } else {
                        new MessageDialog("Ошибка", "Не удалось поместить письма в папку \"Удаленные\"", true);
                    }

                    View.getView().getFrame().getGlassPane().setVisible(false);
                }
            }
        });

        panel.add(deleteButton, "");

        this.add(panel, "cell 0 2, span");
    }

    public void loadData() {
        MailPanel mailPanel = View.getView().getMainPanel().getMailPanel();

        int totalMessages = mailPanel.getTotalMessages();
        int pageSize = mailPanel.getPageSize();

        int totalPages = (totalMessages / pageSize) + (totalMessages % pageSize == 0 ? 0 : 1);

        this.pageLabel.setText("Всего страниц: " + totalPages + ", текущая: " + this.pageField.getText());
    }
}
