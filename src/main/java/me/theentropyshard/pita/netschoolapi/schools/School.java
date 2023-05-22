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

package me.theentropyshard.pita.netschoolapi.schools;

public class School {
    public int provinceId;
    public int cityId;
    public String inn;
    public String ogrn;
    public String address;
    public String shortName;
    public int id;
    public String name;

    public School() {

    }

    @Override
    public String toString() {
        return "SchoolModel{" +
                "provinceId=" + provinceId +
                ", cityId=" + cityId +
                ", inn='" + inn + '\'' +
                ", ogrn='" + ogrn + '\'' +
                ", address='" + address + '\'' +
                ", shortName='" + shortName + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
