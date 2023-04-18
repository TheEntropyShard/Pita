/*      NetSchoolAPI. A simple API client for NetSchool by irTech
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

package me.theentropyshard.netschoolapi.mail;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.theentropyshard.netschoolapi.NetSchoolAPI;
import me.theentropyshard.netschoolapi.Urls;
import me.theentropyshard.netschoolapi.http.ContentType;
import me.theentropyshard.netschoolapi.mail.models.Mail;
import me.theentropyshard.netschoolapi.mail.models.MailSend;
import me.theentropyshard.netschoolapi.mail.models.Message;
import me.theentropyshard.pita.Utils;
import me.theentropyshard.netschoolapi.mail.models.MailEdit;
import okhttp3.Response;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MailService {
    private final NetSchoolAPI api;
    private final ObjectMapper mapper;

    public MailService(NetSchoolAPI api) {
        this.api = api;
        this.mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public int getUnreadMessagesCount() throws IOException {
        try(Response response = this.api.getClient().get(Urls.MAIL_UNREAD_COUNT, new Object[]{"userId", this.api.getStudentId()})) {
            return Integer.parseInt(Utils.readAsOneLine(Objects.requireNonNull(response.body()).byteStream()));
        }
    }

    public Set<Integer> getUnreadMessagesIds() throws IOException {
        try(Response response = this.api.getClient().get(Urls.MAIL_UNREAD, new Object[]{"userId", this.api.getStudentId()})) {
            return new HashSet<>(Arrays.asList(this.mapper.readValue(Objects.requireNonNull(response.body()).charStream(), Integer[].class)));
        }
    }

    public Mail getMail(MailBox mailBox, List<String> fields, MailOrder order, MailSearch search, int page, int pageSize) throws IOException {
        String data = String.format("{\"filterContext\":{\"selectedData\":[{\"filterId\":\"MailBox\",\"filterValue\":\"%s\",\"filterText\":\"%s\"}]},\"fields\":%s,\"page\":%d,\"pageSize\":%d,\"search\":%s,\"order\":%s}",
                mailBox.getFilterValue(),
                mailBox.getFilterText(),
                fields.stream().map(s -> "\"" + s + "\"").collect(Collectors.toList()),
                page,
                pageSize,
                search == null ? "null" : search.getJsonString(),
                order == null ? "{\"fieldId\":\"sent\",\"ascending\":false}" : order.getJsonString());
        try(Response response = this.api.getClient().post(Urls.MAIL_REGISTRY, new Object[0], data, ContentType.JSON)) {
            return this.mapper.readValue(Objects.requireNonNull(response.body()).charStream(), Mail.class);
        }
    }

    public Message readMessage(int messageId) throws IOException {
        try(Response response = this.api.getClient().get(String.format(Urls.MAIL_READ, messageId), new Object[]{"userId", this.api.getStudentId()})) {
            return this.mapper.readValue(Objects.requireNonNull(response.body()).charStream(), Message.class);
        }
    }

    public Response sendMessage(Set<String> receiverIds, Set<File> files, Set<String> fileAttachmentIds, String subject, String text, boolean notify, boolean draft) throws IOException {
        if(receiverIds == null || receiverIds.isEmpty()) {
            throw new IOException("receiverIds == null || receiverIds.length == 0");
        }

        if(text == null || text.trim().isEmpty()) {
            throw new IOException("Текст письма не может быть пустым или null");
        }

        if(text.length() > 65535) {
            throw new IOException("Сообщение слишком большое (длина больше чем 65535 символов)");
        }

        if(fileAttachmentIds == null) {
            fileAttachmentIds = new HashSet<>();
        }

        if(files != null) {
            for(File file : files) {
                try(Response response = this.api.getClient().post(Urls.ATTACHMENTS, new Object[0], file.getAbsolutePath(), ContentType.MULTIPART_FORMDATA)) {
                    fileAttachmentIds.add(Utils.readAsOneLine(Objects.requireNonNull(response.body()).byteStream()));
                }
            }
        }

        MailSend mailSend = new MailSend(
                subject, text, this.api.getStudentId(), new ArrayList<>(fileAttachmentIds),
                notify, 0, new ArrayList<>(receiverIds), draft
        );

        try(Response response = this.api.getClient().post(Urls.MAIL_MESSAGES, new Object[0], this.mapper.writeValueAsString(mailSend), ContentType.JSON)) {
            return response;
        }
    }

    public MailEdit editMessage(MailEditAction action, int messageId) throws IOException {
        try(Response r = this.api.getClient().get(String.format(Urls.MAIL_EDIT, messageId), new Object[] {"action", action.getAction(), "userId", this.api.getStudentId()})) {
            return this.mapper.readValue(Objects.requireNonNull(r.body()).charStream(), MailEdit.class);
        }
    }

    public Response markMessages(boolean read, int... messageIds) throws IOException {
        if(Utils.isBadArray(messageIds)) throw new IllegalArgumentException("Нет сообщений для отметки");

        StringJoiner joiner = new StringJoiner("&=");
        for(int i : messageIds) {
            joiner.add(String.valueOf(i));
        }

        Object[] query = {"userId", this.api.getStudentId(), "read", read};
        try(Response response = this.api.getClient().post(Urls.MAIL_MARK_AS, query, "=" + joiner, ContentType.FORM_URLENCODED)) {
            return response;
        }
    }

    public Response restoreMessages(int... messageIds) throws IOException {
        if(Utils.isBadArray(messageIds)) throw new IllegalArgumentException("Нет сообщений для удаления");

        StringJoiner joiner = new StringJoiner("&=");
        for(int i : messageIds) {
            joiner.add(String.valueOf(i));
        }

        try(Response response = this.api.getClient().post(Urls.MAIL_RESTORE, new Object[]{"userId", this.api.getStudentId()}, "=" + joiner, ContentType.FORM_URLENCODED)) {
            return response;
        }
    }

    public Response deleteMessages(boolean permanent, int... messageIds) throws IOException {
        if(Utils.isBadArray(messageIds)) throw new IllegalArgumentException("Нет сообщений для удаления");

        StringJoiner joiner = new StringJoiner("&=");
        for(int i : messageIds) {
            joiner.add(String.valueOf(i));
        }

        String url = permanent ? Urls.MAIL_DELETE : Urls.MAIL_MOVING;
        try(Response response = this.api.getClient().post(url, new Object[]{"userId", this.api.getStudentId()}, "=" + joiner)) {
            return response;
        }
    }
}
