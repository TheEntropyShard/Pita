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

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.theentropyshard.pita.controller.LoginController;
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
import okhttp3.ResponseBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Response;

import java.net.URL;
import java.net.UnknownHostException;
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

            if (NetSchoolAPI.school == null) {
                LoginService.unexpectedError("Школа '" + this.schoolName + "' не найдена");
                return;
            }

            Call<GetData> getDataCall = NetSchoolAPI.authAPI.getData();
            getDataCall.enqueue(new GetDataCallback(this.login, this.passHash));
        }

        @Override
        public void onFailure(@NotNull Call<School[]> c, @NotNull Throwable t) {
            if (t instanceof UnknownHostException) {
                URL url = c.request().url().url();
                String baseUrl = url.getProtocol() + "://" + url.getHost();
                LoginService.unexpectedError("Некорректный URL-адрес: " + baseUrl);
                return;
            }

            super.onFailure(c, t);
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
            try {
                NetSchoolAPI.at = login.at;
                NetSchoolAPI.userName = login.accountInfo.user.name;
            } catch (Exception e) {
                LOG.error(e);
                LoginService.unexpectedError(e.getMessage());
            }
            Call<DiaryInit> diaryInitCall = NetSchoolAPI.diaryAPI.diaryInit();
            diaryInitCall.enqueue(new DiaryInitCallback());
        }

        @Override
        public void onResponse(@NotNull Call<Login> c, @NotNull Response<Login> r) {
            ResponseBody eBody = r.errorBody();
            if (eBody != null) {
                Scanner s = new Scanner(Objects.requireNonNull(eBody).charStream());
                StringBuilder b = new StringBuilder();
                while (s.hasNextLine()) {
                    b.append(s.nextLine());
                }
                eBody.close();
                s.close();

                String message = new Gson().fromJson(b.toString(), JsonObject.class).get("message").getAsString();
                LoginService.unexpectedError(message);

                return;
            }

            super.onResponse(c, r);
        }
    }

    private static final class DiaryInitCallback extends AbstractCallback<DiaryInit> {
        public DiaryInitCallback() {
            super("DiaryInit");
        }

        @Override
        public void handleResponse(DiaryInit diaryInit) {
            try {
                NetSchoolAPI.userId = diaryInit.students.get(diaryInit.currentStudentId).studentId;
            } catch (Exception e) {
                LOG.error(e);
                LoginService.unexpectedError(e.getMessage());
            }
            SwingUtils.later(() -> AppWindow.window.switchView(StudentView.class.getName()));
        }
    }

    private static void unexpectedError(String msg) {
        LoginController.showErrorDialog(msg);
        LoginController loginController = AppWindow.window.getLoginController();
        loginController.resetLoginButton();
        loginController.resetFields(false, true);
    }
}
