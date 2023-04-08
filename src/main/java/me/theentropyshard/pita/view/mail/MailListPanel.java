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
import me.theentropyshard.pita.view.View;
import me.theentropyshard.pita.view.component.GradientLabel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

public class MailListPanel extends JPanel {
    private final Set<String> selectedRows = new HashSet<>();

    public MailListPanel() {
        this.setLayout(new MigLayout("", "[left][center, grow][center, grow][center]", "[center, fill]"));
        this.setBackground(Color.WHITE);
    }

    public void addNewRecord(String number, String from, String subject, String sent, boolean isRead, boolean isSpecial) {
        GradientLabel numberLabel = new GradientLabel(number, UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
        numberLabel.setOpaque(true);
        numberLabel.setBackground(Color.WHITE);
        numberLabel.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));

        GradientLabel fromLabel = new GradientLabel(from, UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
        fromLabel.setOpaque(true);
        fromLabel.setBackground(Color.WHITE);
        fromLabel.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));

        GradientLabel subjectLabel = new GradientLabel(subject, UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
        subjectLabel.setOpaque(true);
        subjectLabel.setBackground(Color.WHITE);
        subjectLabel.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));

        GradientLabel sentLabel = new GradientLabel(sent, UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
        sentLabel.setOpaque(true);
        sentLabel.setBackground(Color.WHITE);
        sentLabel.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));

        if(!isRead) {
            numberLabel.setFont(numberLabel.getFont().deriveFont(Font.BOLD));
            fromLabel.setFont(fromLabel.getFont().deriveFont(Font.BOLD));
            subjectLabel.setFont(subjectLabel.getFont().deriveFont(Font.BOLD));
            sentLabel.setFont(sentLabel.getFont().deriveFont(Font.BOLD));
        }

        if(!isSpecial) {
            numberLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    Color c = sentLabel.getBackground();
                    if(c == Color.WHITE) {
                        numberLabel.setBackground(Color.LIGHT_GRAY);
                        fromLabel.setBackground(Color.LIGHT_GRAY);
                        subjectLabel.setBackground(Color.LIGHT_GRAY);
                        sentLabel.setBackground(Color.LIGHT_GRAY);
                        selectedRows.add(number);
                    } else {
                        numberLabel.setBackground(Color.WHITE);
                        fromLabel.setBackground(Color.WHITE);
                        subjectLabel.setBackground(Color.WHITE);
                        sentLabel.setBackground(Color.WHITE);
                        selectedRows.remove(number);
                    }
                    repaint();
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    numberLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    numberLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            });

            fromLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    View v = View.getView();
                    v.getMainPanel().getContentLayout().show(v.getMainPanel().getContentPanel(), MailReadPanel.class.getSimpleName());
                    v.getMainPanel().getMailReadPanel().loadData(Integer.parseInt(number) - 1);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    fromLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    fromLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            });

            subjectLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    View v = View.getView();
                    v.getMainPanel().getContentLayout().show(v.getMainPanel().getContentPanel(), MailReadPanel.class.getSimpleName());
                    v.getMainPanel().getMailReadPanel().loadData(Integer.parseInt(number) - 1);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    subjectLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    subjectLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            });

            sentLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    Color c = sentLabel.getBackground();
                    if(c == Color.WHITE) {
                        numberLabel.setBackground(Color.LIGHT_GRAY);
                        fromLabel.setBackground(Color.LIGHT_GRAY);
                        subjectLabel.setBackground(Color.LIGHT_GRAY);
                        sentLabel.setBackground(Color.LIGHT_GRAY);
                        selectedRows.add(number);
                    } else {
                        numberLabel.setBackground(Color.WHITE);
                        fromLabel.setBackground(Color.WHITE);
                        subjectLabel.setBackground(Color.WHITE);
                        sentLabel.setBackground(Color.WHITE);
                        selectedRows.remove(number);
                    }
                    repaint();
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    sentLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    sentLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            });
        }

        this.add(numberLabel);
        this.add(fromLabel);
        this.add(subjectLabel);
        this.add(sentLabel, "wrap");
    }

    public Set<String> getSelectedRows() {
        return this.selectedRows;
    }
}
