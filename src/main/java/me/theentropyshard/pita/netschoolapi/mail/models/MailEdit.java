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

import me.theentropyshard.pita.netschoolapi.diary.models.Attachment;
import me.theentropyshard.pita.netschoolapi.models.IntIdName;
import me.theentropyshard.pita.netschoolapi.models.UserModel;

public class MailEdit {
    public IntIdName author;
    public Attachment[] fileAttachments;
    public float id;
    public String subject;
    public String text;
    public UserModel[] to;
}
