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

package me.theentropyshard.pita.netschoolapi.models;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        UserModel model = (UserModel) o;
        return Objects.equals(id, model.id) && Objects.equals(name, model.name) && Objects.equals(organizationName, model.organizationName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, organizationName);
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
