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

package me.theentropyshard.pita.model.announcements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AnnouncementModel {
    private String subject;
    private String author;
    private String time;
    private String text;
    private List<Map.Entry<String, Integer>> attachments = new ArrayList<>();

    public AnnouncementModel() {

    }

    public AnnouncementModel(String subject, String author, String time, String text) {
        this.subject = subject;
        this.author = author;
        this.time = time;
        this.text = text;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Map.Entry<String, Integer>> getAttachments() {
        return this.attachments;
    }

    public void setAttachments(List<Map.Entry<String, Integer>> attachments) {
        this.attachments = attachments;
    }
}