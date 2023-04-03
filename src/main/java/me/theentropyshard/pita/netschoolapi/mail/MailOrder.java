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

package me.theentropyshard.pita.netschoolapi.mail;

public final class MailOrder {
    private final String jsonString;

    private MailOrder(String jsonString) {
        this.jsonString = jsonString;
    }

    public String getJsonString() {
        return this.jsonString;
    }

    public static MailOrder of(MailField field, boolean ascending) {
        return new MailOrder("{\"fieldId\":\"" + field.getFieldId() + "\",\"ascending\":" + ascending + "}");
    }
}
