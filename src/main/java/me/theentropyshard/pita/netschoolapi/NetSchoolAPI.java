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

package me.theentropyshard.pita.netschoolapi;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.theentropyshard.pita.Utils;
import me.theentropyshard.pita.netschoolapi.diary.DiaryService;
import me.theentropyshard.pita.netschoolapi.diary.models.Announcement;
import me.theentropyshard.pita.netschoolapi.diary.models.Attachment;
import me.theentropyshard.pita.netschoolapi.diary.models.Diary;
import me.theentropyshard.pita.netschoolapi.exceptions.AuthException;
import me.theentropyshard.pita.netschoolapi.exceptions.SchoolNotFoundException;
import me.theentropyshard.pita.netschoolapi.http.HttpClientWrapper;
import me.theentropyshard.pita.netschoolapi.mail.MailBox;
import me.theentropyshard.pita.netschoolapi.mail.MailOrder;
import me.theentropyshard.pita.netschoolapi.mail.MailSearch;
import me.theentropyshard.pita.netschoolapi.mail.MailService;
import me.theentropyshard.pita.netschoolapi.mail.models.Mail;
import me.theentropyshard.pita.netschoolapi.mail.models.Message;
import me.theentropyshard.pita.netschoolapi.models.*;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Response;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

public enum NetSchoolAPI {
    I;

    private final Gson gson = new Gson();
    private final Timer timer = new Timer("PingTimer", true);

    private final List<UserModel> admins = new ArrayList<>();
    private final List<UserModel> headTeachers = new ArrayList<>();
    private final List<UserModel> classroomTeachers = new ArrayList<>();
    private final List<UserModel> teachers = new ArrayList<>();
    private final List<UserModel> classmates = new ArrayList<>();

    private final DiaryService diaryService = new DiaryService(this);
    private final MailService mailService = new MailService(this);

    private HttpClientWrapper client;

    private boolean loggedIn;

    private int yearId;
    private int globalYearId;
    private int studentId;
    private String studentName;
    private String classId;
    private String className;
    private String ver;
    private String at;
    private SchoolModel school;

    NetSchoolAPI() {

    }

    private void findSchool(String schoolName) throws SchoolNotFoundException, IOException {
        String url;
        try(Response response = this.client.get(Urls.SCHOOLS_SEARCH)) {
            url = response.request().url().url().getHost();
            SchoolModel[] schoolStubs = this.gson.fromJson(Objects.requireNonNull(response.body()).charStream(), SchoolModel[].class);
            if(Utils.isBadArray(schoolStubs)) throw new IOException("Школы не найдены: " + url);
            for(SchoolModel schoolModel : schoolStubs) {
                if(!schoolModel.getShortName().equals(schoolName)) continue;
                this.school = schoolModel;
                return;
            }
        }
        throw new SchoolNotFoundException("Не удалось найти школу \"" + schoolName + "\"\n по адресу '" + url + "', скопируйте название школы с сайта");
    }

    public void login(String address, String schoolName, String login, String password, boolean passwordHashed) throws AuthException, SchoolNotFoundException, IOException {
        this.logout();

        address = address.endsWith("/") ? address.substring(0, address.length() - 1) : address;
        this.client = new HttpClientWrapper(address + "/webapi/");

        this.findSchool(schoolName);

        String salt;
        String lt;

        try(Response response = this.client.post(Urls.GET_DATA, "")) {
            JsonObject json = this.gson.fromJson(Objects.requireNonNull(response.body()).charStream(), JsonObject.class);
            if(!json.has("salt")) {
                throw new AuthException("Не удалось получить salt:\n" + Utils.readAsOneLine(Objects.requireNonNull(response.body()).byteStream()));
            }
            this.ver = json.get("ver").getAsString();
            salt = json.get("salt").getAsString();
            lt = json.get("lt").getAsString();
        }

        String pw2;
        if(passwordHashed) {
            pw2 = Utils.md5((salt + password).getBytes(Utils.UTF_8));
        } else {
            pw2 = Utils.md5((salt + Utils.md5(password.getBytes(Charset.forName("windows-1251")))).getBytes(Utils.UTF_8));
        }
        if(pw2 == null) {
            throw new AuthException("Не удалось хэшировать пароль");
        }

        this.findSchool(schoolName);

        String data = Utils.toFormUrlEncoded(
                "LoginType", 1,
                "scid", this.school.getId(),
                "UN", login,
                "lt", lt,
                "pw2", pw2,
                "ver", this.ver
        );

        try(Response response = this.client.post(Urls.LOGIN, data)) {
            JsonObject object = this.gson.fromJson(Objects.requireNonNull(response.body()).charStream(), JsonObject.class);
            if(!object.has("at")) {
                throw new AuthException("Не удалось получить at:" + Utils.readAsOneLine(Objects.requireNonNull(response.body()).byteStream()));
            }
            this.client.addHeader("at", this.at = object.get("at").getAsString());

            boolean pingSGO = Boolean.parseBoolean(System.getProperty("pita.pingSGO"));

            if(pingSGO) {
                String finalAddress = address;
                this.timer.schedule(new TimerTask() {
                    private final HttpClientWrapper httpClient = new HttpClientWrapper(finalAddress + "/webapi/");

                    {
                        httpClient.addHeader("at", at);
                        CookieJar cookieJar = httpClient.getClient().cookieJar();
                        ((HttpClientWrapper.SimpleCookieJar) cookieJar).getCookies().addAll(Cookie.parseAll(response.request().url(), response.headers()));
                    }

                    @Override
                    public void run() {
                        try(Response rsp = httpClient.get("settings/firstLetter")) {
                            if(rsp.isSuccessful()) {
                                System.out.println("pinged");
                            }
                        } catch (IOException ignored) {

                        }
                    }
                }, 0L, object.get("timeOut").getAsLong() - 60000L);
            }

            this.studentName = object.get("accountInfo").getAsJsonObject().get("user").getAsJsonObject().get("name").getAsString();
        }

        try(Response response = this.client.get(Urls.DIARY_INIT)) {
            JsonObject object = this.gson.fromJson(Objects.requireNonNull(response.body()).charStream(), JsonObject.class);
            int currentStudentId = object.get("currentStudentId").getAsInt();
            this.studentId = object.get("students").getAsJsonArray().get(currentStudentId).getAsJsonObject().get("studentId").getAsInt();
        }

        try(Response response = this.client.get(Urls.YEARS_CURRENT)) {
            JsonObject object = this.gson.fromJson(Objects.requireNonNull(response.body()).charStream(), JsonObject.class);
            this.yearId = object.get("id").getAsInt();
            this.globalYearId = object.get("globalYearId").getAsInt();
        }

        try(Response response = this.client.get(Urls.REPORTS + "/studenttotal")) {
            JsonObject object = this.gson.fromJson(Objects.requireNonNull(response.body()).charStream(), JsonObject.class);
            this.classId = object
                    .get("filterSources").getAsJsonArray().get(1)
                    .getAsJsonObject().get("defaultValue").getAsString();
            this.className = object
                    .get("filterSources").getAsJsonArray().get(1)
                    .getAsJsonObject().get("items").getAsJsonArray().get(0)
                    .getAsJsonObject().get("title").getAsString();
        }

        this.loggedIn = true;
    }

    public Diary getDiary(String weekStart, String weekEnd) throws IOException {
        return this.diaryService.getDiary(weekStart, weekEnd);
    }


    public List<Announcement> getAnnouncements(int take) throws IOException {
        try(Response response = this.client.get(Urls.ANNOUNCEMENTS, new Object[]{"take", take})) {
            return Arrays.asList(this.gson.fromJson(Objects.requireNonNull(response.body()).charStream(), Announcement[].class));
        }
    }

    public List<UserSession> getActiveSessions() throws IOException {
        try(Response response = this.client.get(Urls.ACTIVE_SESSIONS)) {
            return Arrays.asList(this.gson.fromJson(Objects.requireNonNull(response.body()).charStream(), UserSession[].class));
        }
    }

    public IntIdName[] getYearlist() throws IOException {
        try(Response r = this.client.get(Urls.YEAR_LIST)) {
            return this.gson.fromJson(Objects.requireNonNull(r.body()).charStream(), IntIdName[].class);
        }
    }

    public MySettings getMySettings() throws IOException {
        try(Response r = this.client.get(Urls.MY_SETTINGS)) {
            return this.gson.fromJson(Objects.requireNonNull(r.body()).charStream(), MySettings.class);
        }
    }

    public SchoolCard getSchoolInfo() throws IOException {
        try(Response response = this.client.get(String.format(Urls.SCHOOL_INFO, this.school.getId()))) {
            return this.gson.fromJson(Objects.requireNonNull(response.body()).charStream(), SchoolCard.class);
        }
    }

    public void downloadAttachment(File file, Attachment attachment) {
        long start = System.currentTimeMillis();
        try {
            String fileName = attachment.name != null ? attachment.name : attachment.originalFileName;
            if(file == null) {
                file = new File(System.getProperty("user.dir") + "/attachments", fileName);
                if(!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
            } else {
                if(file.isDirectory()) {
                    file = new File(file, fileName);
                    if(!file.exists()) {
                        file.createNewFile();
                    }
                }
            }

            try(Response response = this.client.get(String.format(Urls.ATTACHMENTS_DOWNLOAD, attachment.id))) {
                Files.copy(Objects.requireNonNull(response.body()).byteStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Downloaded attachment, took " + (System.currentTimeMillis() - start) + " ms");
    }

    public int getUnreadMessagesCount() throws IOException {
        return this.mailService.getUnreadMessagesCount();
    }

    public Set<Integer> getUnreadMessagesIds() throws IOException {
        return this.mailService.getUnreadMessagesIds();
    }

    public Mail getMail(MailBox mailBox, List<String> fields, MailOrder order, MailSearch search, int page, int pageSize) throws IOException {
        return this.mailService.getMail(mailBox, fields, order, search, page, pageSize);
    }

    public Response sendMessage(List<String> receiverIds, List<File> files, String subject, String text, boolean notify, boolean draft) throws IOException {
        return this.mailService.sendMessage(receiverIds, files, subject, text, notify, draft);
    }

    public Message readMessage(int messageId) throws IOException {
        return this.mailService.readMessage(messageId);
    }

    public Response markMessages(boolean read, int... messageIds) throws IOException {
        return this.mailService.markMessages(read, messageIds);
    }

    public Response restoreMessages(int... messageIds) throws IOException {
        return this.mailService.restoreMessages(messageIds);
    }

    public Response deleteMessages(boolean permanent, int... messageIds) throws IOException {
        return this.mailService.deleteMessages(permanent, messageIds);
    }

    public List<UserModel> getAdmins() {
        if(this.admins.isEmpty()) {
            Object[] params = {
                    "classId", this.getClassId(), "emId", "NaN",
                    "grade", "NaN", "group", 1, "funcType", 2, "orgType", 1,
                    "municipalityId", "NaN",
                    "organizationId", this.school.getId(),
                    "userId", this.getStudentId()
            };

            try(Response response = this.client.get(Urls.MAIL_RECIPIENTS, params)) {
                this.admins.addAll(Arrays.asList(this.gson.fromJson(Objects.requireNonNull(response.body()).charStream(), UserModel[].class)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return this.admins;
    }

    public List<UserModel> getHeadTeachers() {
        if(this.headTeachers.isEmpty()) {
            Object[] params = {
                    "classId", this.getClassId(), "emId", "NaN",
                    "grade", "NaN", "group", 2, "funcType", 2, "orgType", 1,
                    "municipalityId", "NaN",
                    "organizationId", this.school.getId(),
                    "userId", this.getStudentId()
            };

            try(Response response = this.client.get(Urls.MAIL_RECIPIENTS, params)) {
                this.headTeachers.addAll(Arrays.asList(this.gson.fromJson(Objects.requireNonNull(response.body()).charStream(), UserModel[].class)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return this.headTeachers;
    }

    public List<UserModel> getClassroomTeachers() {
        if(this.classroomTeachers.isEmpty()) {
            Object[] params = {
                    "classId", this.getClassId(), "emId", "NaN",
                    "grade", "NaN", "group", 3, "funcType", 2, "orgType", 1,
                    "municipalityId", "NaN",
                    "organizationId", this.school.getId(),
                    "userId", this.getStudentId()
            };

            try(Response response = this.client.get(Urls.MAIL_RECIPIENTS, params)) {
                this.classroomTeachers.addAll(Arrays.asList(this.gson.fromJson(Objects.requireNonNull(response.body()).charStream(), UserModel[].class)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return this.classroomTeachers;
    }

    public List<UserModel> getTeachers() {
        if(this.teachers.isEmpty()) {
            Object[] params = {
                    "classId", this.getClassId(), "emId", "NaN",
                    "grade", "NaN", "group", 4, "funcType", 2, "orgType", 1,
                    "municipalityId", "NaN",
                    "organizationId", this.school.getId(),
                    "userId", this.getStudentId()
            };

            try(Response response = this.client.get(Urls.MAIL_RECIPIENTS, params)) {
                this.teachers.addAll(Arrays.asList(this.gson.fromJson(Objects.requireNonNull(response.body()).charStream(), UserModel[].class)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return this.teachers;
    }

    public List<UserModel> getClassmates() {
        if(this.classmates.isEmpty()) {
            Object[] params = {
                    "classId", this.getClassId(), "emId", "NaN",
                    "grade", "NaN", "group", 7, "funcType", 2, "orgType", 1,
                    "municipalityId", "NaN",
                    "organizationId", this.school.getId(),
                    "userId", this.getStudentId()
            };

            try(Response response = this.client.get(Urls.MAIL_RECIPIENTS, params)) {
                this.classmates.addAll(Arrays.asList(this.gson.fromJson(Objects.requireNonNull(response.body()).charStream(), UserModel[].class)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return this.classmates;
    }

    public void logout() {
        if(this.client != null && this.loggedIn) {
            this.timer.cancel();
            try {
                try(Response response = this.client.post(Urls.LOGOUT, "")) {
                    if(response.isSuccessful()) {
                        this.client = null;
                        this.loggedIn = false;
                        return;
                    }

                    Scanner sc = new Scanner(Objects.requireNonNull(response.body()).byteStream());
                    while(sc.hasNextLine()) {
                        System.out.println(sc.nextLine());
                    }
                    sc.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public SchoolModel getSchool() {
        return this.school;
    }

    public HttpClientWrapper getClient() {
        return this.client;
    }

    /**
     * @return Id года
     */
    public int getYearId() {
        return this.yearId;
    }

    /**
     * @return Номер года, например: 23 (2023)
     */
    public int getGlobalYearId() {
        return this.globalYearId;
    }

    /**
     * @return Id ученика
     */
    public int getStudentId() {
        return this.studentId;
    }

    /**
     * @return Фамилия-Имя ученика
     */
    public String getStudentName() {
        return this.studentName;
    }

    /**
     * @return Id класса ученика
     */
    public String getClassId() {
        return this.classId;
    }

    /**
     * @return Имя класса ученика
     */
    public String getClassName() {
        return this.className;
    }

    public String getVer() {
        return this.ver;
    }

    public String getAt() {
        return this.at;
    }
}
