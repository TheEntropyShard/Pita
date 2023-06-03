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

package me.theentropyshard.pita.netschoolapi.mail;

import me.theentropyshard.pita.netschoolapi.Urls;
import me.theentropyshard.pita.netschoolapi.mail.models.MailRequestEntity;
import me.theentropyshard.pita.netschoolapi.mail.models.MailResponseEntity;
import me.theentropyshard.pita.netschoolapi.mail.models.Message;
import me.theentropyshard.pita.netschoolapi.mail.models.MessageRequestEntity;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Set;

public interface MailAPI {
    @GET(Urls.MAIL_UNREAD_COUNT)
    Call<Integer> getUnreadMessagesCount(@Query("userId") int userId);

    @GET(Urls.MAIL_UNREAD)
    Call<Set<Integer>> getUnreadMessagesIds(@Query("userId") int userId);

    @POST(Urls.MAIL_REGISTRY)
    Call<MailResponseEntity> getMail(@Body MailRequestEntity entity);

    @GET(Urls.MAIL_READ)
    Call<Message> readMessage(@Query("userId") int userId, @Path("id") int id);

    @POST(Urls.MAIL_MESSAGES)
    Call<Void> sendMessage(@Body MessageRequestEntity entity);
}
