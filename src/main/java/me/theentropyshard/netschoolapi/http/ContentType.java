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

import okhttp3.MediaType;

public enum ContentType {
    JSON("application/json; charset=utf-8"),
    FORM_URLENCODED("application/x-www-form-urlencoded; charset=utf-8"),
    MULTIPART_FORMDATA("application/octet-stream");

    private final MediaType mediaType;

    ContentType(String contentType) {
        this.mediaType = MediaType.get(contentType);
    }

    public MediaType getMediaType() {
        return this.mediaType;
    }
}
