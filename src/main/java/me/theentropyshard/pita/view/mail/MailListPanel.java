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
import me.theentropyshard.pita.view.StudentView;
import me.theentropyshard.pita.view.ThemeManager;
import me.theentropyshard.pita.view.View;
import me.theentropyshard.pita.view.component.PGradientLabel;
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
        ThemeManager tm = Pita.getPita().getThemeManager();

        PGradientLabel numberLabel = new PGradientLabel(number);
        numberLabel.setOpaque(true);
        numberLabel.setBackground(tm.getColor("mainColor"));
        numberLabel.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));

        PGradientLabel fromLabel = new PGradientLabel(from);
        fromLabel.setOpaque(true);
        fromLabel.setBackground(tm.getColor("mainColor"));
        fromLabel.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));

        PGradientLabel subjectLabel = new PGradientLabel(subject);
        subjectLabel.setOpaque(true);
        subjectLabel.setBackground(tm.getColor("mainColor"));
        subjectLabel.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));

        PGradientLabel sentLabel = new PGradientLabel(sent);
        sentLabel.setOpaque(true);
        sentLabel.setBackground(tm.getColor("mainColor"));
        sentLabel.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));

        if(!isRead) {
            numberLabel.setFont(numberLabel.getFont().deriveFont(Font.BOLD));
            fromLabel.setFont(fromLabel.getFont().deriveFont(Font.BOLD));
            subjectLabel.setFont(subjectLabel.getFont().deriveFont(Font.BOLD));
            sentLabel.setFont(sentLabel.getFont().deriveFont(Font.BOLD));
        }

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Object source = e.getSource();
                if(source == fromLabel || source == subjectLabel) {
                    StudentView mp = View.getView().getMainPanel();
                    mp.getContentLayout().show(mp.getContentPanel(), MailReadPanel.class.getSimpleName());
                    mp.getMailReadPanel().loadData(Integer.parseInt(number) - 1);
                } else if(source == numberLabel || source == sentLabel) {
                    ThemeManager tm = Pita.getPita().getThemeManager();
                    Color mainColor = tm.getColor("mainColor");
                    Color c = sentLabel.getBackground();
                    if(c.equals(mainColor) || c.equals(tm.getColor("ultraLightAccentColor"))) {
                        numberLabel.setBackground(Color.LIGHT_GRAY);
                        fromLabel.setBackground(Color.LIGHT_GRAY);
                        subjectLabel.setBackground(Color.LIGHT_GRAY);
                        sentLabel.setBackground(Color.LIGHT_GRAY);
                        selectedRows.add(number);
                    } else {
                        numberLabel.setBackground(mainColor);
                        fromLabel.setBackground(mainColor);
                        subjectLabel.setBackground(mainColor);
                        sentLabel.setBackground(mainColor);
                        selectedRows.remove(number);
                    }
                    repaint();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if(!selectedRows.contains(number)) {
                    ThemeManager tm = Pita.getPita().getThemeManager();
                    Color ultraLightAccentColor = tm.getColor("ultraLightAccentColor");
                    numberLabel.setBackground(ultraLightAccentColor);
                    fromLabel.setBackground(ultraLightAccentColor);
                    subjectLabel.setBackground(ultraLightAccentColor);
                    sentLabel.setBackground(ultraLightAccentColor);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(!selectedRows.contains(number)) {
                    ThemeManager tm = Pita.getPita().getThemeManager();
                    Color mainColor = tm.getColor("mainColor");
                    numberLabel.setBackground(mainColor);
                    fromLabel.setBackground(mainColor);
                    subjectLabel.setBackground(mainColor);
                    sentLabel.setBackground(mainColor);
                }
            }
        };

        if(!isSpecial) {
            numberLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            numberLabel.addMouseListener(mouseAdapter);

            fromLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            fromLabel.addMouseListener(mouseAdapter);

            subjectLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            subjectLabel.addMouseListener(mouseAdapter);

            sentLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            sentLabel.addMouseListener(mouseAdapter);
        }

        this.add(numberLabel);
        this.add(fromLabel);
        this.add(subjectLabel);
        this.add(sentLabel, "wrap");
    }

    @Override
    public void removeAll() {
        super.removeAll();
        this.selectedRows.clear();
    }

    public Set<String> getSelectedRows() {
        return this.selectedRows;
    }
}
