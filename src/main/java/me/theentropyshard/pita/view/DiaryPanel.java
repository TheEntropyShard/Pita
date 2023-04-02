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
import me.theentropyshard.pita.netschoolapi.diary.models.Assignment;
import me.theentropyshard.pita.netschoolapi.diary.models.Day;
import me.theentropyshard.pita.netschoolapi.diary.models.Diary;
import me.theentropyshard.pita.netschoolapi.diary.models.Lesson;
import me.theentropyshard.pita.view.component.ui.VerticalLabelUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DiaryPanel extends JPanel {
    private static final DateTimeFormatter DAY_DATE_FORMATTER = DateTimeFormatter.ofPattern("EEEE',' dd MMMM yyyy 'г.'");

    private final DiaryPanelElement[] days;
    private final JPanel panel;

    private boolean verticalOrder;

    public DiaryPanel() {
        super(new BorderLayout());
        this.setBackground(Color.WHITE);

        this.panel = new JPanel();
        this.panel.setBackground(Color.WHITE);
        this.panel.setLayout(new GridLayout(3, 2));

        this.add(this.panel);

        this.days = new DiaryPanelElement[6];
        for(int i = 0; i < this.days.length; i++) {
            this.days[i] = new DiaryPanelElement();
        }

        this.arrangeDays(this.verticalOrder);
    }

    public void loadData() {
        if(true) {
            try {
                //Diary diary = NetSchoolAPI.I.getDiary("2023-02-13", "2023-02-19");
                Diary diary = NetSchoolAPI.I.getDiary("", "");
                for(int dayNum = 0; dayNum < diary.weekDays.length; dayNum++) {
                    Day day = diary.weekDays[dayNum];
                    DiaryPanelElement element = this.days[dayNum];
                    element.setDate(LocalDateTime.parse(day.date).format(DiaryPanel.DAY_DATE_FORMATTER));
                    String[][] data = element.getRowData();
                    for(int lessonNum = 0; lessonNum < Math.min(day.lessons.length, 8); lessonNum++) {
                        Lesson lesson = day.lessons[lessonNum];
                        String[] lessonArr = data[lesson.number - 1];
                        lessonArr[0] = lesson.number + ". " + lesson.subjectName;
                        if(lesson.assignments != null) {
                            for(Assignment assign : lesson.assignments) {
                                if(assign.mark != null) {
                                    lessonArr[2] = String.valueOf(assign.mark.mark);
                                } else {
                                    lessonArr[1] = assign.assignmentName;
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void arrangeDays(boolean verticalOrder) {
        this.verticalOrder = verticalOrder;
        this.panel.removeAll();

        if(verticalOrder) {
            for(int i = 0; i < this.days.length - 3; i++) {
                this.panel.add(this.days[i]);
                this.panel.add(this.days[i + 3]);
            }
        } else {
            for(DiaryPanelElement day : this.days) {
                this.panel.add(day);
            }
        }
    }

    private static class DiaryPanelElement extends JPanel {
        private final JLabel dateLabel;
        private final String[][] rowData;
        private final JTable table;

        public DiaryPanelElement() {
            this.setLayout(new BorderLayout());

            this.dateLabel = new JLabel() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setPaint(new GradientPaint(0, 0, UIConstants.DARK_GREEN, this.getWidth(), this.getHeight(), UIConstants.LIGHT_GREEN));
                    g2.fillRect(0, 0, this.getWidth(), this.getHeight());
                    super.paintComponent(g2);
                }
            };
            this.dateLabel.setHorizontalAlignment(JLabel.CENTER);
            this.dateLabel.setForeground(Color.WHITE);
            this.dateLabel.setUI(new VerticalLabelUI(false));
            this.dateLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
            this.add(this.dateLabel, BorderLayout.WEST);

            JPanel panel = new JPanel(new BorderLayout());

            this.setDate("Нет данных");

            Object[] colNames = {"Урок", "Домашнее задание", "Оценка"};
            this.rowData = new String[8][3];
            for(int i = 0; i < 8; i++) {
                for(int j = 0; j < 3; j++) {
                    this.rowData[i][j] = "";
                }
            }

            DefaultTableModel tableModel = new DefaultTableModel(this.rowData, colNames) {
                @Override
                public int getColumnCount() {
                    return 3;
                }

                @Override
                public int getRowCount() {
                    return 8;
                }

                @Override
                public Object getValueAt(int row, int column) {
                    return rowData[row][column];
                }
            };

            this.table = new JTable(tableModel);
            this.table.getColumn("Урок").setMinWidth(UIConstants.DEFAULT_MIN_WIDTH_COLUMN);
            this.table.getColumn("Урок").setMaxWidth(UIConstants.DEFAULT_MAX_WIDTH_COLUMN);
            this.dateLabel.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    Component component = e.getComponent();
                    table.setRowHeight((component.getHeight() - table.getTableHeader().getHeight()) / 8);
                }
            });

            panel.add(table, BorderLayout.CENTER);
            panel.add(table.getTableHeader(), BorderLayout.NORTH);
            this.add(panel, BorderLayout.CENTER);
        }

        public void setDate(String date) {
            this.dateLabel.setText(date);
        }

        public String[][] getRowData() {
            return this.rowData;
        }

        public JTable getTable() {
            return this.table; //TODO изменять ширину колонок по содержимому
        }
    }
}
