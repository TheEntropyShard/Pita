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

import com.google.gson.Gson;
import me.theentropyshard.pita.Utils;
import me.theentropyshard.pita.netschoolapi.NetSchoolAPI;
import me.theentropyshard.pita.netschoolapi.Urls;
import me.theentropyshard.pita.netschoolapi.diary.models.Assignment;
import me.theentropyshard.pita.netschoolapi.diary.models.DetailedAssignment;
import me.theentropyshard.pita.netschoolapi.diary.models.Diary;
import okhttp3.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DiaryService {
    private final NetSchoolAPI api;
    private final Gson gson;

    public DiaryService(NetSchoolAPI api) {
        this.api = api;
        this.gson = new Gson();
    }

    public Diary getDiary(String weekStart, String weekEnd) throws IOException {
        if(weekStart == null || weekStart.isEmpty()) weekStart = Utils.getCurrentWeekStart();
        if(weekEnd == null || weekEnd.isEmpty()) weekEnd = Utils.getCurrentWeekEnd();

        System.out.println(weekStart);
        System.out.println(weekEnd);

        Object[] query = {
                "studentId", this.api.getStudentId(), "vers", this.api.getVer(),
                "weekStart", weekStart, "weekEnd", weekEnd, "yearId", this.api.getYearId(),
                "withLaAssigns", true
        };

        try(Response response = this.api.getClient().get(Urls.DIARY, query)) {
            return this.gson.fromJson(Objects.requireNonNull(response.body()).charStream(), Diary.class);
        }
    }

    public List<Assignment> getOverdueJobs(String weekStart, String weekEnd) throws IOException {
        if(weekStart == null || weekStart.isEmpty()) weekStart = Utils.getCurrentWeekStart();
        if(weekEnd == null || weekEnd.isEmpty()) weekEnd = Utils.getCurrentWeekEnd();

        Object[] query = {
                "studentId", this.api.getStudentId(), "vers", this.api.getVer(),
                "weekStart", weekStart, "weekEnd", weekEnd, "yearId", this.api.getYearId(),
        };

        try(Response response = this.api.getClient().get(Urls.OVERDUE, query)) {
            return Arrays.asList(this.gson.fromJson(Objects.requireNonNull(response.body()).charStream(), Assignment[].class));
        }
    }

    public DetailedAssignment getDetailedAssignment(int assignmentId) throws IOException {
        String query = "?studentId=" + this.api.getStudentId();
        try(Response response = this.api.getClient().get(Urls.ASSIGNS + "/" + assignmentId, new Object[] {"studentId", this.api.getStudentId()})) {
            return this.gson.fromJson(Objects.requireNonNull(response.body()).charStream(), DetailedAssignment.class);
        }
    }
}
