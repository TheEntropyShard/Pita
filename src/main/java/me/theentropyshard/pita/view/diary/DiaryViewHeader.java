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
package me.theentropyshard.pita.view.diary;
import me.theentropyshard.pita.Pita;
import me.theentropyshard.pita.date.DateUtils;
import me.theentropyshard.pita.date.Week;
import me.theentropyshard.pita.netschoolapi.NetSchoolAPI_old;
import me.theentropyshard.pita.netschoolapi.models.Term;
import me.theentropyshard.pita.view.ThemeManager;
import me.theentropyshard.pita.view.component.PComboBox;
import me.theentropyshard.pita.view.component.PSimpleButton;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiaryViewHeader extends JPanel {
    private final PComboBox comboBox;
    private final PSimpleButton prevButton;
    private final PSimpleButton nextButton;

    private final Map<Week, Map.Entry<LocalDate, LocalDate>> weeks;

    private String selectedWeekStart = DateUtils.getCurrentWeekStart();
    private String selectedWeekEnd = DateUtils.getCurrentWeekEnd();

    public DiaryViewHeader(DiaryView diaryView) {
        this.setLayout(new MigLayout("", "push[center]15[center]15[center]push", "[center]"));

        ThemeManager tm = Pita.getPita().getThemeManager();
        this.setBackground(tm.getColor("mainColor"));

        this.weeks = new HashMap<>();

        this.prevButton = new PSimpleButton("<");
        this.prevButton.setRoundCorners(true);
        this.prevButton.setSquareSides(true);
        this.nextButton = new PSimpleButton(">");
        this.nextButton.setRoundCorners(true);
        this.nextButton.setSquareSides(true);
        this.comboBox = new PComboBox();
        this.comboBox.setPreferredSize(new Dimension(250, this.prevButton.getPreferredSize().height));
        this.comboBox.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 5));
        this.comboBox.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
        this.comboBox.addItemListener(e -> {
            //TODO
            //diaryView.loadData();
        });

        this.comboBox.addItem("Hello");
        this.comboBox.addItem("World");
        this.comboBox.addItem("Ok");
        this.comboBox.addItem("Good");
        this.prevButton.addActionListener(e -> this.comboBox.selectPrev());
        this.nextButton.addActionListener(e -> this.comboBox.selectNext());
        this.add(this.prevButton);
        this.add(this.comboBox, "w 25%");
        this.add(this.nextButton);
    }
    public void loadData() {
        List<Term> terms = NetSchoolAPI_old.I.getTerms();
        Term term = terms.get(3);// четвертая четверть
        List<Week> weeks = DateUtils.getWeeks(term.startDate, term.endDate);
        Week current = null;
        for(Week week : weeks) {
            week.days.forEach(localDate -> this.comboBox.addItem(localDate.toString()));
            //week.days.forEach(localDate -> this.comboBox.addItem(localDate.toString()));
            if(week.days.get(0).toString().equals(this.selectedWeekStart)) {
                current = week;
            }
            this.weeks.put(week, new AbstractMap.SimpleEntry<>(week.days.get(0), week.days.get(5)));
        }

    }

    public Map<Week, Map.Entry<LocalDate, LocalDate>> getWeeks() {
        return this.weeks;
    }

    public String getSelectedWeekStart() {
        return this.selectedWeekStart;
    }

    public String getSelectedWeekEnd() {
        return this.selectedWeekEnd;
    }

    public PComboBox getComboBox() {
        return this.comboBox;
    }
    public PSimpleButton getPrevButton() {
        return this.prevButton;
    }
    public PSimpleButton getNextButton() {
        return this.nextButton;
    }
}