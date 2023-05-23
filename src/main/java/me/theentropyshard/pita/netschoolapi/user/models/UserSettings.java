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

package me.theentropyshard.pita.netschoolapi.user.models;

import java.util.List;

public class UserSettings {
    public boolean showMobilePhone;
    public int defaultDesktop;
    public String language;
    public List<Object> favoriteReports;
    public int passwordExpired;
    public String recoveryAnswer;
    public String recoveryQuestion;
    public int theme;
    public int userId;
    public boolean showNetSchoolApp;
    public boolean showSferumBanner;

    @Override
    public String toString() {
        return "UserSettings{" +
                "showMobilePhone=" + this.showMobilePhone +
                ", defaultDesktop=" + this.defaultDesktop +
                ", language='" + this.language + '\'' +
                ", favoriteReports=" + this.favoriteReports +
                ", passwordExpired=" + this.passwordExpired +
                ", recoveryAnswer='" + this.recoveryAnswer + '\'' +
                ", recoveryQuestion='" + this.recoveryQuestion + '\'' +
                ", theme=" + this.theme +
                ", userId=" + this.userId +
                ", showNetSchoolApp=" + this.showNetSchoolApp +
                ", showSferumBanner=" + this.showSferumBanner +
                '}';
    }
}
