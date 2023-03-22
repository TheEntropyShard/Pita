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

package me.theentropyshard.pita.netschoolapi.models;

public class UserModel {
    public String id;
    public String name;
    public String organizationName;

    public UserModel() {

    }

    public UserModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserModel(String id, String name, String organizationName) {
        this.id = id;
        this.name = name;
        this.organizationName = organizationName;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", organizationName='" + organizationName + '\'' +
                '}';
    }
}
