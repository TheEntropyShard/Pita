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

import me.theentropyshard.netschoolapi.NetSchoolAPI;
import me.theentropyshard.netschoolapi.models.UserModel;
import me.theentropyshard.pita.view.BorderPanel;
import me.theentropyshard.pita.view.UIConstants;
import me.theentropyshard.pita.view.View;
import me.theentropyshard.pita.view.component.GradientLabel;
import me.theentropyshard.pita.view.component.PComboBox;
import me.theentropyshard.pita.view.component.PScrollBar;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.*;

public class MailChooseRecipientDialog extends JDialog {
    private final Set<UserModel> recipients;

    public MailChooseRecipientDialog() {
        super(View.getView().getFrame(), "Выбор получателей", true);

        this.recipients = new HashSet<>();

        MigLayout layout = new MigLayout("fill, ltr", "[fill, center][fill, center]", "[top]");
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(layout);

        BorderPanel leftPanel = new BorderPanel();
        BorderPanel rightPanel = new BorderPanel();

        PComboBox receiversCategory = new PComboBox();
        receiversCategory.addItem("Администраторы");
        receiversCategory.addItem("Завучи");
        receiversCategory.addItem("Классные руководители");
        receiversCategory.addItem("Учителя");
        receiversCategory.addItem("Ученики");

        receiversCategory.setFont(new Font("JetBrains Mono", Font.BOLD, 14));

        leftPanel.addComponent(receiversCategory);

        JPanel selectableUsers = new JPanel();
        selectableUsers.setOpaque(false);
        selectableUsers.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(UIConstants.DARK_GREEN, 1),
                        "Выберите получателей из списка",
                        TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                        new Font("JetBrains Mono", Font.BOLD, 12),
                        UIConstants.DARK_GREEN
                )
        );
        selectableUsers.setLayout(new BoxLayout(selectableUsers, BoxLayout.PAGE_AXIS));

        JPanel selectedUsers = new JPanel();
        selectedUsers.setOpaque(false);
        selectedUsers.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(UIConstants.DARK_GREEN, 1),
                        "Выбранные получатели",
                        TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                        new Font("JetBrains Mono", Font.BOLD, 12),
                        UIConstants.DARK_GREEN
                )
        );
        selectedUsers.setLayout(new BoxLayout(selectedUsers, BoxLayout.PAGE_AXIS));

        leftPanel.addComponent(selectableUsers);
        rightPanel.addComponent(selectedUsers);

        receiversCategory.setSelectedIndex(2);

        receiversCategory.addItemListener(ie -> {
            selectableUsers.removeAll();

            String item = (String) receiversCategory.getSelectedItem();
            List<UserModel> users = new ArrayList<>();
            System.out.println(item);
            switch(Objects.requireNonNull(item)) {
                case "Администраторы":
                    users.addAll(NetSchoolAPI.I.getAdmins());
                    break;
                case "Завучи":
                    users.addAll(NetSchoolAPI.I.getHeadTeachers());
                    break;
                case "Классные руководители":
                    users.addAll(NetSchoolAPI.I.getClassroomTeachers());
                    break;
                case "Учителя":
                    users.addAll(NetSchoolAPI.I.getTeachers());
                    break;
                case "Ученики":
                    users.addAll(NetSchoolAPI.I.getClassmates());
                    break;
            }
            for(UserModel user : users) {
                GradientLabel label = new GradientLabel(user.name, UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
                label.setFont(new Font("JetBrains Mono", Font.BOLD, 12));
                label.setBorder(new EmptyBorder(0, 5, 3, 0));
                label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                label.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        GradientLabel label = new GradientLabel(user.name, UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
                        label.setFont(new Font("JetBrains Mono", Font.BOLD, 12));
                        label.setBorder(new EmptyBorder(0, 5, 3, 0));
                        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        label.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mousePressed(MouseEvent e) {
                                if(recipients.contains(user)) {
                                    recipients.remove(user);
                                    selectedUsers.remove(label);
                                    selectedUsers.revalidate();
                                }
                            }
                        });
                        if(!recipients.contains(user)) {
                            recipients.add(user);
                            selectedUsers.add(label);
                            selectedUsers.revalidate();
                        }
                    }
                });
                selectableUsers.add(label);
            }
            selectableUsers.revalidate();
        });

        rightPanel.getInternalPanel().setPreferredSize(leftPanel.getInternalPanel().getPreferredSize());

        panel.add(leftPanel);
        panel.add(rightPanel);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setViewportView(panel);
        scrollPane.setVerticalScrollBar(new PScrollBar());

        this.add(scrollPane);
        this.getContentPane().setPreferredSize(new Dimension(800, 522));
        this.pack();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public Set<UserModel> getRecipients() {
        return this.recipients;
    }
}
