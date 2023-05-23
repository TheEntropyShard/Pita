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

import me.theentropyshard.pita.netschoolapi.announcements.AnnouncementsAPI;
import me.theentropyshard.pita.netschoolapi.auth.AuthAPI;
import me.theentropyshard.pita.netschoolapi.diary.DiaryAPI;
import me.theentropyshard.pita.netschoolapi.mail.MailAPI;
import me.theentropyshard.pita.netschoolapi.schools.School;
import me.theentropyshard.pita.netschoolapi.schools.SchoolsAPI;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class NetSchoolAPI {
    public static String at = "";
    public static String ver = "";
    public static School school;
    public static int userId;

    public static SchoolsAPI schoolsAPI;
    public static AuthAPI authAPI;
    public static AnnouncementsAPI announcementsAPI;
    public static MailAPI mailAPI;
    public static DiaryAPI diaryAPI;

    public static void init(String baseUrl) {
        NetSchoolAPI.at = "";
        NetSchoolAPI.ver = "";

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .cookieJar(new SimpleCookieJar())
                .connectTimeout(60L, TimeUnit.SECONDS)
                .callTimeout(60L, TimeUnit.SECONDS)
                .readTimeout(60L, TimeUnit.SECONDS)
                .writeTimeout(60L, TimeUnit.SECONDS);

        boolean disableSSL = Boolean.parseBoolean(System.getProperty("netschoolapi.disableSSL"));
        if (disableSSL) {
            X509TrustManager x509TrustManager = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };


            SSLContext sslContext = null;
            try {
                sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, new TrustManager[]{x509TrustManager}, new SecureRandom());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (sslContext != null) {
                builder.sslSocketFactory(sslContext.getSocketFactory(), x509TrustManager);
            } else {
                System.err.println("Не удалось отключить проверку SSL");
            }
        }

        builder.addInterceptor(chain -> {
            Request request = chain.request();

            Request.Builder newRequest = request.newBuilder()
                    .addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
                    .addHeader("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3")
                    .addHeader("X-Requested-With", "XMLHttpRequest")
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
                    .addHeader("Referer", baseUrl)
                    .addHeader("Origin", baseUrl);
            if (NetSchoolAPI.at != null && !NetSchoolAPI.at.isEmpty()) {
                newRequest.addHeader("at", NetSchoolAPI.at);
            }

            return chain.proceed(newRequest.build());
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NetSchoolAPI.schoolsAPI = retrofit.create(SchoolsAPI.class);
        NetSchoolAPI.authAPI = retrofit.create(AuthAPI.class);
        NetSchoolAPI.announcementsAPI = retrofit.create(AnnouncementsAPI.class);
        NetSchoolAPI.mailAPI = retrofit.create(MailAPI.class);
        NetSchoolAPI.diaryAPI = retrofit.create(DiaryAPI.class);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> NetSchoolAPI.authAPI.logout()));
    }

    public static class SimpleCookieJar implements CookieJar {
        private final List<Cookie> cookies = new ArrayList<>();

        @NotNull
        @Override
        public List<Cookie> loadForRequest(@NotNull HttpUrl httpUrl) {
            this.cookies.removeIf(cookie -> cookie.expiresAt() < System.currentTimeMillis());
            return this.cookies.stream().filter(cookie -> cookie.matches(httpUrl)).collect(Collectors.toList());
        }

        @Override
        public void saveFromResponse(@NotNull HttpUrl httpUrl, @NotNull List<Cookie> cookies) {
            this.cookies.addAll(cookies);
        }
    }

    private NetSchoolAPI() {
        throw new UnsupportedOperationException("Class NetSchoolAPI should not be instantiated");
    }
}
