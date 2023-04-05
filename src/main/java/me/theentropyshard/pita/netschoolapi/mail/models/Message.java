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

package me.theentropyshard.pita.netschoolapi.mail.models;

import me.theentropyshard.pita.netschoolapi.diary.models.Attachment;
import me.theentropyshard.pita.netschoolapi.models.UserModel;

import java.util.Arrays;

/**
 * Класс, представляющий сообщение
 */
public class Message {
    public int id;
    public String text;
    public String subject;
    public String sent;
    public UserModel author;
    public Attachment[] fileAttachments;
    public UserModel[] to;
    public String toNames;
    public Object[] cc;
    public String ccNames;
    public Object[] bcc;
    public String mailBox;
    public boolean noReply;
    public boolean canReplyAll;
    public boolean canForward;
    public boolean canEdit;

    public Message() {

    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", subject='" + subject + '\'' +
                ", sent='" + sent + '\'' +
                ", author=" + author +
                ", fileAttachments=" + Arrays.toString(fileAttachments) +
                ", to=" + Arrays.toString(to) +
                ", toNames='" + toNames + '\'' +
                ", cc=" + Arrays.toString(cc) +
                ", ccNames='" + ccNames + '\'' +
                ", bcc=" + Arrays.toString(bcc) +
                ", mailBox='" + mailBox + '\'' +
                ", noReply=" + noReply +
                ", canReplyAll=" + canReplyAll +
                ", canForward=" + canForward +
                ", canEdit=" + canEdit +
                '}';
    }
}
