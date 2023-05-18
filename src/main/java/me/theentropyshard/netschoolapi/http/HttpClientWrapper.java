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
package me.theentropyshard.netschoolapi.http;

import me.theentropyshard.pita.Utils;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class HttpClientWrapper {
    private final OkHttpClient client;
    private final Map<String, String> globalHeaders;
    private final String baseUrl;

    public HttpClientWrapper(String baseUrl) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .cookieJar(new SimpleCookieJar())
                .readTimeout(60L, TimeUnit.SECONDS)
                .writeTimeout(60L, TimeUnit.SECONDS)
                .connectTimeout(60L, TimeUnit.SECONDS)
                .callTimeout(60L, TimeUnit.SECONDS)
                .hostnameVerifier((hostname, session) -> baseUrl.contains(hostname));

        boolean disableSSL = Boolean.parseBoolean(System.getProperty("netschoolapi.disableSSL"));

        if(disableSSL) {
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

            if(sslContext != null) {
                builder.sslSocketFactory(sslContext.getSocketFactory(), x509TrustManager);
            } else {
                System.err.println("Не удалось отключить проверку SSL");
            }
        }

        this.client = builder.build();
        this.globalHeaders = new HashMap<>();
        this.baseUrl = baseUrl;

        this.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        this.addHeader("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3");
        this.addHeader("X-Requested-With", "XMLHttpRequest");
        this.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36");
        this.addHeader("Origin", this.baseUrl);
    }

    public Request buildRequest(String url, Object[] queryParams,String data, Map<String, String> headers, RequestType reqType, ContentType conType) {
        url = url.startsWith("http") || url.startsWith("ws") ? url : this.baseUrl + url;

        url += "?" + Utils.toFormUrlEncoded(queryParams);

        Request.Builder builder = new Request.Builder().url(url);

        if(reqType == RequestType.POST) {
            RequestBody requestBody;
            if(conType == ContentType.MULTIPART_FORMDATA) {
                File file = new File(data);
                requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("data", String.format("{\"name\":\"%s\",\"description\":\"\"}", file.getName()))
                        .addFormDataPart("file", file.getName(), RequestBody.create(file, conType.getMediaType()))
                        .build();
            } else {
                requestBody = RequestBody.create(data, conType.getMediaType());
            }
            builder.post(requestBody);
        } else {
            builder.get();
        }

        headers.forEach(builder::header);
        builder.header("Referer", url);
        this.globalHeaders.forEach(builder::header);
        return builder.build();
    }

    public Response executeRequest(Request request) throws IOException {
        return this.client.newCall(request).execute();
    }

    public Response get(String url) throws IOException {
        return this.get(url, new Object[0]);
    }

    public Response get(String url, Object[] queryParams) throws IOException {
        return this.get(url, queryParams, Collections.emptyMap());
    }

    public Response get(String url, Object[] queryParams, Map<String, String> headers) throws IOException {
        Request request = this.buildRequest(url, queryParams,"", headers, RequestType.GET, ContentType.FORM_URLENCODED);
        return this.executeRequest(request);
    }

    public Response post(String url, String data) throws IOException {
        return this.post(url, new Object[0], data);
    }
    public Response post(String url, Object[] queryParams, String data) throws IOException {
        return this.post(url, queryParams, data, ContentType.FORM_URLENCODED);
    }

    public Response post(String url, Object[] queryParams, String data, ContentType contentType) throws IOException {
        return this.post(url, queryParams, data, Collections.emptyMap(), contentType);
    }

    public Response post(String url, Object[] queryParams, String data, Map<String, String> headers, ContentType contentType) throws IOException {
        Request request = this.buildRequest(url, queryParams, data, headers, RequestType.POST, contentType);
        return this.executeRequest(request);
    }

    public void addHeader(String name, String value) {
        this.globalHeaders.put(name, value);
    }

    public OkHttpClient getClient() {
        return this.client;
    }

    public enum RequestType {
        GET,
        POST
    }

    /**
     * Simple implementation of CookieJar
     * https://gist.github.com/johannes-staehlin/63a72467bd1f21829d11bc55456c5836
     */
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

        public List<Cookie> getCookies() {
            return this.cookies;
        }
    }
}