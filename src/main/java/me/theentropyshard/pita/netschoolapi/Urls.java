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

/**
 * Класс, содержащий все используемые URLы
 */
public enum Urls {
    ;

    //@formatter:off

    public static final String MY_SETTINGS          = "mysettings";
    public static final String YEAR_LIST            = "mysettings/yearlist";
    public static final String SCHOOLS_SEARCH       = "schools/search";
    public static final String SCHOOL_INFO          = "schools/%d/card";
    public static final String ANNOUNCEMENTS        = "announcements";
    public static final String YEARS_CURRENT        = "years/current";
    public static final String ACTIVE_SESSIONS      = "context/activeSessions";
    public static final String ASSIGNMENT_TYPES     = "grade/assignment/types";

    /**
     * Auth
     */
    public static final String GET_DATA             = "auth/getdata";
    public static final String LOGIN                = "login";
    public static final String LOGOUT               = "auth/logout";

    /**
     * SignalR
     */
    public static final String NEGOTIATE            = "signalr/negotiate";
    public static final String START                = "signalr/start";
    public static final String ABORT                = "signalr/abort";
    public static final String SEND                 = "signalr/send";
    public static final String CONNECT              = "signalr/connect";

    /**
     * Reports
     */
    public static final String REPORTS              = "reports/";

    /**
     * Diary
     */
    public static final String DIARY                = "student/diary";
    public static final String DIARY_INIT           = "student/diary/init";
    public static final String ASSIGNS              = "student/diary/assigns";
    public static final String OVERDUE              = "student/diary/pastMandatory";
    public static final String GET_ATTACHMENTS      = "student/diary/get-attachments";

    /**
     * Attachments
     */
    public static final String ATTACHMENTS          = "attachments/";
    public static final String ATTACHMENTS_DOWNLOAD = "attachments/%d";
    public static final String UPLOAD_LIMITS        = "attachments/uploadLimits";

    /**
     * Users
     */
    public static final String USERS_STUDENT_LIST   = "users/studentlist";
    public static final String USERS_STAFF          = "users/staff";

    /**
     * Mail
     */
    public static final String MAIL_MESSAGES        = "mail/messages";
    public static final String MAIL_REGISTRY        = "mail/registry";
    public static final String MAIL_RECIPIENTS      = "mail/recipients";
    public static final String MAIL_READ            = "mail/messages/%d/read";
    public static final String MAIL_EDIT            = "mail/messages/%d/edit";
    public static final String MAIL_RESTORE         = "mail/messages/restore";
    public static final String MAIL_UNREAD          = "mail/messages/unread";
    public static final String MAIL_MARK_AS         = "mail/messages/mark-as";
    public static final String MAIL_UNREAD_COUNT    = "mail/messages/unread-count";
    public static final String MAIL_MOVING          = "mail/messages/moving";
    public static final String MAIL_DELETE          = "mail/messages/delete";

    //@formatter:on
}
