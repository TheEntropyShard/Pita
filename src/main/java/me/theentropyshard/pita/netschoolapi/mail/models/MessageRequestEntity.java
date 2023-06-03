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

package me.theentropyshard.pita.netschoolapi.mail.models;

import java.util.List;

public class MessageRequestEntity {
    public String subject;
    public String text;
    public int authorId;
    public List<String> fileAttachmentIds;
    public boolean notify;
    public int id;
    public List<String> to;
    public boolean draft;

    public MessageRequestEntity(String subject,
                                String text,
                                int authorId,
                                List<String> fileAttachmentIds,
                                boolean notify,
                                int id,
                                List<String> to,
                                boolean draft) {
        this.subject = subject;
        this.text = text;
        this.authorId = authorId;
        this.fileAttachmentIds = fileAttachmentIds;
        this.notify = notify;
        this.id = id;
        this.to = to;
        this.draft = draft;
    }

    @Override
    public String toString() {
        return "MessageRequestEntity{" +
                "subject='" + this.subject + '\'' +
                ", text='" + this.text + '\'' +
                ", authorId=" + this.authorId +
                ", fileAttachmentIds=" + this.fileAttachmentIds +
                ", notify=" + this.notify +
                ", id=" + this.id +
                ", to=" + this.to +
                ", draft=" + this.draft +
                '}';
    }
}
