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

import me.theentropyshard.pita.netschoolapi.NetSchoolAPI_old;
import me.theentropyshard.pita.view.*;
import me.theentropyshard.pita.view.component.PGradientLabel;
import me.theentropyshard.pita.view.component.PComboBox;
import me.theentropyshard.pita.view.component.PTextField;
import me.theentropyshard.pita.view.component.PSimpleButton;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;

public class MailPanelHeader extends JPanel {
    private final PGradientLabel pageLabel;
    private final PTextField pageField;
    private final PComboBox mailBoxComboBox;
    private final PComboBox searchComboBox;
    private final PTextField searchField;
    private final PTextField pageSizeField;
    private final PSimpleButton loadButton;
    private final PSimpleButton writeButton;
    private final PSimpleButton deleteButton;

    public MailPanelHeader(MailView mailView) {
        this.setLayout(new MigLayout("flowy", "[left]15[left]5[left]15[left]15[left]push", "[center][center][center]"));
        this.setBackground(Color.WHITE);

        PGradientLabel label = new PGradientLabel("Почтовая папка");
        label.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
        this.add(label, "cell 0 0");

        this.mailBoxComboBox = new PComboBox();
        this.mailBoxComboBox.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
        this.mailBoxComboBox.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 5));
        this.mailBoxComboBox.addItem("Входящие");
        this.mailBoxComboBox.addItem("Отправленные");
        this.mailBoxComboBox.addItem("Удаленные");
        this.mailBoxComboBox.addItem("Черновики");

        this.mailBoxComboBox.addItemListener(e -> {
            String item = (String) this.mailBoxComboBox.getSelectedItem();
            switch(Objects.requireNonNull(item)) {
                case "Входящие":
                    //mailView.setMailBox(MailBox.BOX_INCOMING);
                    break;
                case "Отправленные":
                    //mailView.setMailBox(MailBox.BOX_SENT);
                    break;
                case "Удаленные":
                    //mailView.setMailBox(MailBox.BOX_DELETED);
                    break;
                case "Черновики":
                    //mailView.setMailBox(MailBox.BOX_DRAFTS);
                    break;
                default:
                    throw new RuntimeException("Unreachable: " + item);
            }
            mailView.loadData();
        });

        this.add(this.mailBoxComboBox, "cell 0 1");

        PGradientLabel searchLabel = new PGradientLabel("Поиск");
        searchLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
        this.add(searchLabel, "cell 1 0");

        this.searchComboBox = new PComboBox();
        this.searchComboBox.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
        this.searchComboBox.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 5));
        this.searchComboBox.addItem("От кого");
        this.searchComboBox.addItem("Кому");
        this.searchComboBox.addItem("Тема");

        this.searchComboBox.addItemListener(e -> {
            String item = (String) this.searchComboBox.getSelectedItem();
            switch(Objects.requireNonNull(item)) {
                case "От кого":
                   // mailView.setSearchField(MailField.AUTHOR);
                    break;
                case "Кому":
                  //  mailView.setSearchField(MailField.TO_NAMES);
                    break;
                case "Тема":
                   // mailView.setSearchField(MailField.SUBJECT);
                    break;
                default:
                    throw new RuntimeException("Unreachable: " + item);
            }
        });

        this.searchField = new PTextField();
        this.searchField.setHint("Введите текст...");
        this.searchField.setFont(new Font("JetBrains Mono", Font.BOLD, 14));

        Dimension preferredSize = this.searchField.getPreferredSize();
        this.searchField.setPreferredSize(new Dimension(250, preferredSize.height));

        this.mailBoxComboBox.setPreferredSize(new Dimension(this.mailBoxComboBox.getPreferredSize().width, preferredSize.height));
        this.searchComboBox.setPreferredSize(new Dimension(this.searchComboBox.getPreferredSize().width, preferredSize.height));

        this.add(this.searchComboBox, "cell 1 1");
        this.add(this.searchField, "cell 2 1");

        this.searchComboBox.setPreferredSize(this.mailBoxComboBox.getPreferredSize());

        PGradientLabel numberLabel = new PGradientLabel("Число записей на странице");
        numberLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));

        this.add(numberLabel, "cell 3 0");

        this.pageSizeField = new PTextField();
        this.pageSizeField.setPreferredSize(new Dimension(250, this.searchField.getPreferredSize().height));
        this.pageSizeField.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
        this.pageSizeField.setText("20");

        this.add(this.pageSizeField, "cell 3 1");

        this.pageLabel = new PGradientLabel("");
        this.pageLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));

        this.add(this.pageLabel, "cell 4 0");

        this.pageField = new PTextField();
        this.pageField.setText("1");
        this.pageField.setPreferredSize(new Dimension(250, this.searchField.getPreferredSize().height));
        this.pageField.setFont(new Font("JetBrains Mono", Font.BOLD, 14));

        this.add(this.pageField, "cell 4 1");

        JPanel panel = new JPanel(new MigLayout("insets 0, fillx"));
        panel.setBackground(Color.WHITE);

        this.loadButton = new PSimpleButton("Загрузить");
        this.loadButton.addActionListener(e -> {

        });
        this.loadButton.setRoundCorners(true);

        panel.add(this.loadButton, "");

        this.writeButton = new PSimpleButton("Написать");
        this.writeButton.setRoundCorners(true);
        this.writeButton.addActionListener(e -> {
            /*View v = View.getView();
            StudentView mp = v.getMainPanel();
            mp.getContentLayout().show(mp.getContentPanel(), MailWriteView.class.getSimpleName());*/
        });

        panel.add(this.writeButton, "");

        this.deleteButton = new PSimpleButton("Удалить");
        this.deleteButton.setRoundCorners(true);
        this.deleteButton.addActionListener(e -> {
            Set<String> selectedRows = mailView.getMailListPanel().getSelectedRows();
           // MailRecord[] rows = mailView.getRows();

            int[] messageIds = new int[selectedRows.size()];

            int i = 0;
            for(String s : selectedRows) {
                //messageIds[i] = rows[Integer.parseInt(s) - 1].id;
                i++;
            }

            if(selectedRows.size() != 0) {
                /*View.getView().getFrame().getGlassPane().setVisible(true);

                MessageDialog dialog = new MessageDialog("Подтверждение", "Вы хотите выбранные письма в папку \"Удаленные\"?", true);

                View.getView().getFrame().getGlassPane().setVisible(false);

                if(dialog.getResult() == MessageDialog.Result.OK) {
                    View.getView().getFrame().getGlassPane().setVisible(true);

                    boolean success = true;

                    try {
                        NetSchoolAPI_old.I.deleteMessages(false, messageIds);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        success = false;
                    }

                    if(success) {
                        new MessageDialog("Внимание", "Ваши письма помещены в папку \"Удаленные\"", true);
                        StudentView studentView = View.getView().getMainPanel();
                        studentView.getMailPanel().loadData();
                        studentView.getContentLayout().show(studentView.getContentPanel(), MailView.class.getSimpleName());
                    } else {
                        new MessageDialog("Ошибка", "Не удалось поместить письма в папку \"Удаленные\"", true);
                    }

                    View.getView().getFrame().getGlassPane().setVisible(false);
                }*/
            }
        });

        panel.add(this.deleteButton, "");

        this.add(panel, "cell 0 2, span");
    }

    public void loadData() {
    }

    public PGradientLabel getPageLabel() {
        return this.pageLabel;
    }

    public PTextField getPageField() {
        return this.pageField;
    }

    public PComboBox getMailBoxComboBox() {
        return this.mailBoxComboBox;
    }

    public PComboBox getSearchComboBox() {
        return this.searchComboBox;
    }

    public PTextField getSearchField() {
        return this.searchField;
    }

    public PTextField getPageSizeField() {
        return this.pageSizeField;
    }

    public PSimpleButton getLoadButton() {
        return this.loadButton;
    }

    public PSimpleButton getWriteButton() {
        return this.writeButton;
    }

    public PSimpleButton getDeleteButton() {
        return this.deleteButton;
    }
}
