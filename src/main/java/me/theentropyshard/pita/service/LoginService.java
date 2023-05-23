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
import me.theentropyshard.pita.utils.AbstractCallback;
import me.theentropyshard.pita.utils.SwingUtils;
import me.theentropyshard.pita.utils.Utils;
import me.theentropyshard.pita.view.AppWindow;
import me.theentropyshard.pita.view.StudentView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import retrofit2.Call;

import java.nio.charset.StandardCharsets;

public class LoginService {
    private static final Logger LOG = LogManager.getLogger(LoginService.class);

    public LoginService() {

    }

    public void login(String baseUrl, String schoolName, String login, String passHash) {
        NetSchoolAPI.init(baseUrl);

        Call<School[]> schoolsCall = NetSchoolAPI.schoolsAPI.getSchools();
        schoolsCall.enqueue(new SchoolsCallback(schoolName, login, passHash));
    }

    private static final class SchoolsCallback extends AbstractCallback<School[]> {
        private final String schoolName;
        private final String login;
        private final String passHash;

        private SchoolsCallback(String schoolName, String login, String passHash) {
            super("Schools");

            this.schoolName = schoolName;
            this.login = login;
            this.passHash = passHash;
        }

        @Override
        public void handleResponse(School[] schools) {
            for (School school : schools) {
                if (this.schoolName.equals(school.shortName)) {
                    NetSchoolAPI.school = school;
                    break;
                }
            }
            Call<GetData> getDataCall = NetSchoolAPI.authAPI.getData();
            getDataCall.enqueue(new GetDataCallback(this.login, this.passHash));
        }
    }

    private static final class GetDataCallback extends AbstractCallback<GetData> {
        private final String login;
        private final String passHash;

        public GetDataCallback(String login, String passHash) {
            super("GetData");

            this.login = login;
            this.passHash = passHash;
        }

        @Override
        public void handleResponse(GetData getData) {
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
        }
    }

    private static final class LoginCallback extends AbstractCallback<Login> {
        public LoginCallback() {
            super("Login");
        }

        @Override
        public void handleResponse(Login login) {
            NetSchoolAPI.at = login.at;
            NetSchoolAPI.userName = login.accountInfo.user.name;
            Call<DiaryInit> diaryInitCall = NetSchoolAPI.diaryAPI.diaryInit();
            diaryInitCall.enqueue(new DiaryInitCallback());
        }
    }

    private static final class DiaryInitCallback extends AbstractCallback<DiaryInit> {
        public DiaryInitCallback() {
            super("DiaryInit");
        }

        @Override
        public void handleResponse(DiaryInit diaryInit) {
            NetSchoolAPI.userId = diaryInit.students.get(diaryInit.currentStudentId).studentId;
            SwingUtils.later(() -> AppWindow.window.switchView(StudentView.class.getName()));
        }
    }
}
