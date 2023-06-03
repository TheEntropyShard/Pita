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

    public static final String SGO_PING             = "webapi/settings/firstLetter";
    public static final String MY_SETTINGS          = "webapi/mysettings";
    public static final String YEAR_LIST            = "webapi/mysettings/yearlist";
    public static final String SCHOOLS_SEARCH       = "webapi/schools/search";
    public static final String SCHOOL_INFO          = "webapi/schools/%d/card";
    public static final String ANNOUNCEMENTS        = "webapi/announcements";
    public static final String YEARS_CURRENT        = "webapi/years/current";
    public static final String ACTIVE_SESSIONS      = "webapi/context/activeSessions";
    public static final String TERMS_SEARCH         = "webapi/terms/search";
    public static final String ASSIGNMENT_TYPES     = "webapi/grade/assignment/types";

    /**
     * Auth
     */
    public static final String GET_DATA             = "webapi/auth/getdata";
    public static final String LOGIN                = "webapi/login";
    public static final String LOGOUT               = "webapi/auth/logout";

    /**
     * SignalR
     */
    public static final String NEGOTIATE            = "webapi/signalr/negotiate";
    public static final String START                = "webapi/signalr/start";
    public static final String ABORT                = "webapi/signalr/abort";
    public static final String SEND                 = "webapi/signalr/send";
    public static final String CONNECT              = "webapi/signalr/connect";

    /**
     * Reports
     */
    public static final String REPORTS              = "webapi/reports/";

    /**
     * Diary
     */
    public static final String DIARY                = "webapi/student/diary";
    public static final String DIARY_INIT           = "webapi/student/diary/init";
    public static final String ASSIGNS              = "webapi/student/diary/assigns";
    public static final String OVERDUE              = "webapi/student/diary/pastMandatory";
    public static final String GET_ATTACHMENTS      = "webapi/student/diary/get-attachments";

    /**
     * Attachments
     */
    public static final String ATTACHMENTS          = "webapi/attachments/";
    public static final String ATTACHMENTS_DOWNLOAD = "webapi/attachments/%d";
    public static final String UPLOAD_LIMITS        = "webapi/attachments/uploadLimits";

    /**
     * Users
     */
    public static final String USER_SETTINGS        = "webapi/usersettings";
    public static final String USERS_STUDENT_LIST   = "webapi/users/studentlist";
    public static final String USERS_STAFF          = "webapi/users/staff";

    /**
     * Mail
     */
    public static final String MAIL_MESSAGES        = "webapi/mail/messages";
    public static final String MAIL_REGISTRY        = "webapi/mail/registry";
    public static final String MAIL_RECIPIENTS      = "webapi/mail/recipients";
    public static final String MAIL_READ            = "webapi/mail/messages/{id}/read";
    public static final String MAIL_EDIT            = "webapi/mail/messages/%d/edit";
    public static final String MAIL_RESTORE         = "webapi/mail/messages/restore";
    public static final String MAIL_UNREAD          = "webapi/mail/messages/unread";
    public static final String MAIL_MARK_AS         = "webapi/mail/messages/mark-as";
    public static final String MAIL_UNREAD_COUNT    = "webapi/mail/messages/unread-count";
    public static final String MAIL_MOVING          = "webapi/mail/messages/moving";
    public static final String MAIL_DELETE          = "webapi/mail/messages/delete";

    //@formatter:on
}
