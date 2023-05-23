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

package me.theentropyshard.pita.service;

import me.theentropyshard.pita.netschoolapi.NetSchoolAPI;
import me.theentropyshard.pita.netschoolapi.auth.models.GetData;
import me.theentropyshard.pita.netschoolapi.auth.models.Login;
import me.theentropyshard.pita.netschoolapi.diary.models.DiaryInit;
import me.theentropyshard.pita.netschoolapi.schools.School;
import me.theentropyshard.pita.netschoolapi.user.models.UserSettings;
import me.theentropyshard.pita.utils.SwingUtils;
import me.theentropyshard.pita.utils.Utils;
import me.theentropyshard.pita.view.AppWindow;
import me.theentropyshard.pita.view.StudentView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

public class LoginService {
    private static final Logger LOG = LogManager.getLogger(LoginService.class);

    public LoginService() {

    }

    public void login(String baseUrl, String schoolName, String login, String passHash) {
        NetSchoolAPI.init(baseUrl);

        Call<School[]> schoolsCall = NetSchoolAPI.schoolsAPI.getSchools();
        schoolsCall.enqueue(new SchoolsCallback(schoolName, login, passHash));
    }

    private static final class SchoolsCallback implements Callback<School[]> {
        private final String schoolName;
        private final String login;
        private final String passHash;

        private SchoolsCallback(String schoolName, String login, String passHash) {
            this.schoolName = schoolName;
            this.login = login;
            this.passHash = passHash;
        }

        @Override
        public void onResponse(@NotNull Call<School[]> c, @NotNull Response<School[]> r) {
            School[] schools = r.body();
            if (schools != null) {
                for (School school : schools) {
                    if (this.schoolName.equals(school.shortName)) {
                        NetSchoolAPI.school = school;
                        break;
                    }
                }
                Call<GetData> getDataCall = NetSchoolAPI.authAPI.getData();
                getDataCall.enqueue(new GetDataCallback(this.login, this.passHash));
            } else {
                Scanner scanner = new Scanner(Objects.requireNonNull(r.errorBody()).charStream());
                StringBuilder builder = new StringBuilder();
                while (scanner.hasNextLine()) {
                    builder.append(scanner.nextLine());
                }
                scanner.close();
                LOG.warn("Schools response body is null, code: {}, message: {}", r.code(), builder.toString());
            }
        }

        @Override
        public void onFailure(@NotNull Call<School[]> c, @NotNull Throwable t) {
            LOG.error("[SchoolsCallback]: " + t);
        }
    }

    private static final class GetDataCallback implements Callback<GetData> {
        private final String login;
        private final String passHash;

        public GetDataCallback(String login, String passHash) {
            this.login = login;
            this.passHash = passHash;
        }

        @Override
        public void onResponse(@NotNull Call<GetData> c, @NotNull Response<GetData> r) {
            GetData getData = r.body();
            if (getData != null) {
                NetSchoolAPI.ver = getData.ver;
                String pw2 = Utils.md5((getData.salt + this.passHash).getBytes(StandardCharsets.UTF_8));
                if (pw2 == null) {
                    LOG.warn("Cannot md5 password: null");
                } else {
                    Call<Login> loginCall = NetSchoolAPI.authAPI.login(
                            1, NetSchoolAPI.school.id, this.login, getData.lt, pw2, NetSchoolAPI.ver
                    );
                    loginCall.enqueue(new LoginCallback());
                }
            } else {
                Scanner scanner = new Scanner(Objects.requireNonNull(r.errorBody()).charStream());
                StringBuilder builder = new StringBuilder();
                while (scanner.hasNextLine()) {
                    builder.append(scanner.nextLine());
                }
                scanner.close();
                LOG.warn("GetData response body is null, code: {}, message: {}", r.code(), builder.toString());
            }
        }

        @Override
        public void onFailure(@NotNull Call<GetData> c, @NotNull Throwable t) {
            LOG.error("[GetDataCallback]: " + t);
        }
    }

    private static final class LoginCallback implements Callback<Login> {
        @Override
        public void onResponse(@NotNull Call<Login> c, @NotNull Response<Login> r) {
            Login login = r.body();
            if (login != null) {
                NetSchoolAPI.at = login.at;
                Call<DiaryInit> diaryInitCall = NetSchoolAPI.diaryAPI.diaryInit();
                diaryInitCall.enqueue(new DiaryInitCallback());
            } else {
                Scanner scanner = new Scanner(Objects.requireNonNull(r.errorBody()).charStream());
                StringBuilder builder = new StringBuilder();
                while (scanner.hasNextLine()) {
                    builder.append(scanner.nextLine());
                }
                scanner.close();
                LOG.warn("Login response body is null, code: {}, message: {}", r.code(), builder.toString());
            }
        }

        @Override
        public void onFailure(@NotNull Call<Login> c, @NotNull Throwable t) {
            LOG.error("[LoginCallback]: " + t);
        }
    }

    private static final class DiaryInitCallback implements Callback<DiaryInit> {
        @Override
        public void onResponse(Call<DiaryInit> c, Response<DiaryInit> r) {
            DiaryInit diaryInit = r.body();
            if (diaryInit != null) {
                NetSchoolAPI.userId = diaryInit.students.get(diaryInit.currentStudentId).studentId;
                SwingUtils.later(() -> AppWindow.window.switchView(StudentView.class.getName()));
            } else {
                Scanner scanner = new Scanner(Objects.requireNonNull(r.errorBody()).charStream());
                StringBuilder builder = new StringBuilder();
                while (scanner.hasNextLine()) {
                    builder.append(scanner.nextLine());
                }
                scanner.close();
                LOG.warn("Login response body is null, code: {}, message: {}", r.code(), builder.toString());
            }
        }

        @Override
        public void onFailure(Call<DiaryInit> c, Throwable t) {
            LOG.error("[DiaryInitCallback]: " + t);
        }
    }
}
