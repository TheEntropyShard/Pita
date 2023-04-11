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

import me.theentropyshard.netschoolapi.NetSchoolAPI;
import me.theentropyshard.netschoolapi.diary.models.Assignment;
import me.theentropyshard.netschoolapi.diary.models.Day;
import me.theentropyshard.netschoolapi.diary.models.Diary;
import me.theentropyshard.netschoolapi.diary.models.Lesson;
import me.theentropyshard.pita.view.component.GradientLabel;
import me.theentropyshard.pita.view.component.PScrollBar;
import me.theentropyshard.pita.view.mail.InfoPanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DiaryPanel extends JPanel {
    private static final DateTimeFormatter DAY_DATE_FORMATTER = DateTimeFormatter.ofPattern("EEEE',' dd MMMM yyyy 'г.'");

    public static final int TYPE_ID_LESSON_ANSWER = 10;
    public static final int TYPE_ID_HOMEWORK = 3;

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
        if(true) {
            try {
                //Diary diary = NetSchoolAPI.I.getDiary("2023-02-13", "2023-02-19");
                Diary diary = NetSchoolAPI.I.getDiary("2023-04-10", "2023-04-15");
                for(int dayNum = 0; dayNum < diary.weekDays.length; dayNum++) {
                    Day diaryDay = diary.weekDays[dayNum];
                    DiaryDay dayElement = this.days[dayNum];
                    for(int lessonNum = 0; lessonNum < diaryDay.lessons.length; lessonNum++) {
                        Lesson lesson = diaryDay.lessons[lessonNum];
                        DiaryLesson diaryLesson = new DiaryLesson(" ", " ");
                        try {
                            diaryLesson = dayElement.lessons.get(lesson.number - 1);
                        } catch (IndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }
                        diaryLesson.lessonNameLabel.setText(diaryLesson.lessonNameLabel.getText() + lesson.subjectName);
                        if(lesson.assignments != null) {
                            for(Assignment assignment : lesson.assignments) {
                                if(assignment.typeId == DiaryPanel.TYPE_ID_HOMEWORK) {
                                    diaryLesson.homeworkLabel.setText(assignment.assignmentName);
                                } else if(assignment.mark != null) {
                                    diaryLesson.addMark(String.valueOf(assignment.mark.mark));
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
            this.daysPanel.add(this.days[0], "cell 0 0, growx"); /* Пн | Чт */
            this.daysPanel.add(this.days[1], "cell 0 1, growx"); /* Вт | Пт */
            this.daysPanel.add(this.days[2], "cell 0 2, growx"); /* Ср | Сб */
            this.daysPanel.add(this.days[3], "cell 1 0, growx");
            this.daysPanel.add(this.days[4], "cell 1 1, growx");
            this.daysPanel.add(this.days[5], "cell 1 2, growx");
        } else {
            this.daysPanel.add(this.days[0], "cell 0 0, growx"); /* Пн | Вт */
            this.daysPanel.add(this.days[1], "cell 1 0, growx"); /* Ср | Чт */
            this.daysPanel.add(this.days[2], "cell 0 1, growx"); /* Пт | Сб */
            this.daysPanel.add(this.days[3], "cell 1 1, growx");
            this.daysPanel.add(this.days[4], "cell 0 2, growx");
            this.daysPanel.add(this.days[5], "cell 1 2, growx");
        }
    }

    private static class DiaryDay extends JPanel {
        private final List<DiaryLesson> lessons;

        public DiaryDay() {
            this.lessons = new ArrayList<>();

            this.setLayout(new MigLayout("fill", "[25%][60%][15%]", "[]"));

            this.addHeader(new DiaryLesson("Урок", "Домашнее задание") {{
                this.addMark("Оценка");
            }});

            for(int i = 0; i < 8; i++) {
                this.add(new DiaryLesson((i + 1) + ". ", " "));
            }
        }

        public void addHeader(DiaryLesson lesson) {
            this.add(lesson.getLessonNameLabel(), "center");
            this.add(lesson.getHomeworkLabel(), "center");
            this.add(lesson.getMarksPanel(), "center, wrap");
        }

        public void add(DiaryLesson lesson) {
            this.lessons.add(lesson);
            this.add(lesson.getLessonNameLabel(), "growx, width 0:0:100%");
            this.add(lesson.getHomeworkLabel(), "growx, width 0:0:100%");
            this.add(lesson.getMarksPanel(), "center, wrap");
        }

        public List<DiaryLesson> getLessons() {
            return this.lessons;
        }
    }

    private static class DiaryLesson {
        private final GradientLabel lessonNameLabel;
        private final GradientLabel homeworkLabel;
        private final JPanel marksPanel;

        public DiaryLesson(String lessonName, String homework) {
            this.lessonNameLabel = new GradientLabel(lessonName, UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
            this.lessonNameLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));

            this.homeworkLabel = new GradientLabel(homework, UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
            this.homeworkLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));

            this.marksPanel = new JPanel(new MigLayout("insets 0", "[center]", "[center]"));
        }

        public void addMark(String mark) {
            int iMark = 0;
            try {
                iMark = Integer.parseInt(mark);
            } catch (NumberFormatException ignored) {

            }
            GradientLabel label = iMark >= 3 ? new GradientLabel(mark, UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN) :
                    Character.isDigit(mark.charAt(0)) ?
                    new GradientLabel(mark, new Color(105, 0, 0), new Color(168, 0, 0)) :
                            new GradientLabel(mark, UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
            label.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
            this.marksPanel.add(label);
        }

        public GradientLabel getLessonNameLabel() {
            return this.lessonNameLabel;
        }

        public GradientLabel getHomeworkLabel() {
            return this.homeworkLabel;
        }

        public JPanel getMarksPanel() {
            return this.marksPanel;
        }
    }
}
