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

package me.theentropyshard.pita.netschoolapi.diary;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.theentropyshard.pita.netschoolapi.Urls;
import me.theentropyshard.pita.netschoolapi.diary.models.Assignment;
import me.theentropyshard.pita.netschoolapi.diary.models.DetailedAssignment;
import me.theentropyshard.pita.netschoolapi.diary.models.Diary;
import me.theentropyshard.pita.netschoolapi.NetSchoolAPI_old;
import me.theentropyshard.pita.date.DateUtils;
import okhttp3.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DiaryService {
    private final NetSchoolAPI_old api;
    private final ObjectMapper mapper;

    public DiaryService(NetSchoolAPI_old api) {
        this.api = api;
        this.mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private static String[] checkDates(String weekStart, String weekEnd) {
        if(weekStart == null || weekStart.isEmpty()) {
            weekStart = DateUtils.getCurrentWeekStart();
        }

        if(weekEnd == null || weekEnd.isEmpty()) {
            weekEnd = DateUtils.getCurrentWeekEnd();
        }

        return new String[] {weekStart, weekEnd};
    }

    public Diary getDiary(String weekStart, String weekEnd) throws IOException {
        String[] dates = DiaryService.checkDates(weekStart, weekEnd);

        Object[] query = {
                "studentId", this.api.getStudentId(), "vers", this.api.getVer(),
                "weekStart", dates[0], "weekEnd", dates[1], "yearId", this.api.getYearId(),
                "withLaAssigns", true
        };

        try(Response response = this.api.getClient().get(Urls.DIARY, query)) {
            return this.mapper.readValue(Objects.requireNonNull(response.body()).charStream(), Diary.class);
        }
    }

    public List<Assignment> getOverdueJobs(String weekStart, String weekEnd) throws IOException {
        String[] dates = DiaryService.checkDates(weekStart, weekEnd);

        Object[] query = {
                "studentId", this.api.getStudentId(), "vers", this.api.getVer(),
                "weekStart", dates[0], "weekEnd", dates[1], "yearId", this.api.getYearId(),
        };

        try(Response response = this.api.getClient().get(Urls.OVERDUE, query)) {
            return Arrays.asList(this.mapper.readValue(Objects.requireNonNull(response.body()).charStream(), Assignment[].class));
        }
    }

    public DetailedAssignment getDetailedAssignment(int assignmentId) throws IOException {
        Object[] query = {"studentId", this.api.getStudentId()};
        try(Response response = this.api.getClient().get(Urls.ASSIGNS + "/" + assignmentId, query)) {
            return this.mapper.readValue(Objects.requireNonNull(response.body()).charStream(), DetailedAssignment.class);
        }
    }
}
