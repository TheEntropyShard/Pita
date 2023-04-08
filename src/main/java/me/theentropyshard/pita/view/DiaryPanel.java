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
import me.theentropyshard.pita.view.component.GradientLabel;
import me.theentropyshard.pita.view.component.PScrollBar;
import me.theentropyshard.pita.view.mail.InfoPanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class DiaryPanel extends JPanel {
    private static final DateTimeFormatter DAY_DATE_FORMATTER = DateTimeFormatter.ofPattern("EEEE',' dd MMMM yyyy 'г.'");

    private final DiaryDay[] days;
    private final JPanel daysPanel;

    private boolean verticalOrder;

    public DiaryPanel() {
        super(new BorderLayout());
        this.setBackground(Color.WHITE);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new MigLayout("fillx", "[0:0:100%, fill]", "[]"));

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setViewportView(panel);
        scrollPane.setVerticalScrollBar(new PScrollBar());
        scrollPane.setHorizontalScrollBar(new PScrollBar() {{
            this.setOrientation(JScrollBar.HORIZONTAL);
        }});

        this.daysPanel = new JPanel();
        this.daysPanel.setBackground(Color.WHITE);
        this.daysPanel.setLayout(new GridLayout(3, 2, 5, 5));

        InfoPanel infoPanel = new InfoPanel();
        infoPanel.addDataPanel(this.daysPanel);

        panel.add(infoPanel);

        this.add(scrollPane);

        this.days = new DiaryDay[6];
        for(int i = 0; i < this.days.length; i++) {
            this.days[i] = new DiaryDay();
        }

        this.arrangeDays(this.verticalOrder);
    }

    public void loadData() {
        if(false) {
            try {
                //Diary diary = NetSchoolAPI.I.getDiary("2023-02-13", "2023-02-19");
                Diary diary = NetSchoolAPI.I.getDiary("", "");
                for(int dayNum = 0; dayNum < diary.weekDays.length; dayNum++) {
                    Day day = diary.weekDays[dayNum];
                    DiaryDay element = this.days[dayNum];
                    //element.setDate(LocalDateTime.parse(day.date).format(DiaryPanel.DAY_DATE_FORMATTER));
                    String[][] data = new String[8][3];
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
        this.daysPanel.removeAll();

        if(verticalOrder) {
            for(int i = 0; i < this.days.length - 3; i++) {
                this.daysPanel.add(this.days[i]);
                this.daysPanel.add(this.days[i + 3]);
            }
        } else {
            this.daysPanel.add(this.days[0], "cell 0 0, growx");
            this.daysPanel.add(this.days[1], "cell 1 0, growx");
            this.daysPanel.add(this.days[2], "cell 0 1, growx");
            this.daysPanel.add(this.days[3], "cell 1 1, growx");
            this.daysPanel.add(this.days[4], "cell 0 2, growx");
            this.daysPanel.add(this.days[5], "cell 1 2, growx");
        }
    }

    private static class DiaryDay extends JPanel {
        public DiaryDay() {
            this.setLayout(new MigLayout("fill", "[15%][70%][15%]", "[]"));

            this.addHeader(new DiaryLesson("Урок", "Домашнее задание", "Оценка"));

            for(int i = 0; i < 8; i++) {
                this.add(new DiaryLesson("Урок " + i, "Задание", "Оценка " + i));
            }
        }

        public void addHeader(DiaryLesson lesson) {
            this.add(lesson.getLessonNameLabel(), "center");
            this.add(lesson.getHomeworkLabel(), "center");
            this.add(lesson.getMarksLabel(), "center, wrap");
        }

        public void add(DiaryLesson lesson) {
            this.add(lesson.getLessonNameLabel(), "growx, width 0:0:100%");
            this.add(lesson.getHomeworkLabel(), "growx, width 0:0:100%");
            this.add(lesson.getMarksLabel(), "center, wrap");
        }
    }

    private static class DiaryLesson {
        private GradientLabel lessonNameLabel;
        private GradientLabel homeworkLabel;
        private GradientLabel marksLabel;

        public DiaryLesson(String lessonName, String homework, String marks) {
            this.lessonNameLabel = new GradientLabel(lessonName, UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
            this.lessonNameLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));

            this.homeworkLabel = new GradientLabel(homework, UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
            this.homeworkLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));

            this.marksLabel = new GradientLabel(marks, UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
            this.marksLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
        }

        public GradientLabel getLessonNameLabel() {
            return this.lessonNameLabel;
        }

        public GradientLabel getHomeworkLabel() {
            return this.homeworkLabel;
        }

        public GradientLabel getMarksLabel() {
            return this.marksLabel;
        }
    }
}
