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

package me.theentropyshard.pita.date;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public enum DateUtils {
    ;

    public static LocalDateTime startOfWeek(String date) {
        return LocalDateTime.parse(date).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

    public static LocalDateTime endOfWeek(String date) {
        return LocalDateTime.parse(date).with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
    }

    public static List<Week> getWeeks(String startDate, String endDate) {
        List<Week> weeks = new ArrayList<>();

        LocalDateTime start = DateUtils.startOfWeek(startDate);
        long days = ChronoUnit.DAYS.between(start, DateUtils.endOfWeek(endDate));
        for(int i = 0; i < days; i++) {
            if(i % 7 == 0) {
                Week week = new Week();
                for(int j = 0; j < 7; j++) {
                    week.days.add(start.plusDays(j));
                }
                weeks.add(week);
            }
        }

        return weeks;
    }
}
