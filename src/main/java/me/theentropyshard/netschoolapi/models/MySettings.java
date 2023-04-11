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

package me.theentropyshard.netschoolapi.models;

import java.util.Arrays;

public class MySettings {
    public int userId;
    public String firstName;
    public String lastName;
    public String middleName;
    public String loginName;
    public String birthDate;
    public String[] roles;
    public int schoolyearId;
    public String windowsAccount = null;
    public String mobilePhone = null;
    public String preferedCom;
    public String email;
    public boolean existsPhoto;
    public UserSettings userSettings;

    @Override
    public String toString() {
        return "MySettings{" +
                "userId=" + this.userId +
                ", firstName='" + this.firstName + '\'' +
                ", lastName='" + this.lastName + '\'' +
                ", middleName='" + this.middleName + '\'' +
                ", loginName='" + this.loginName + '\'' +
                ", birthDate='" + this.birthDate + '\'' +
                ", roles=" + Arrays.toString(this.roles) +
                ", schoolyearId=" + this.schoolyearId +
                ", windowsAccount='" + this.windowsAccount + '\'' +
                ", mobilePhone='" + this.mobilePhone + '\'' +
                ", preferedCom='" + this.preferedCom + '\'' +
                ", email='" + this.email + '\'' +
                ", existsPhoto=" + this.existsPhoto +
                ", userSettings=" + this.userSettings +
                '}';
    }

    public static class UserSettings {
        public boolean showMobilePhone;
        public int defaultDesktop;
        public String language;
        public Object[] favoriteReports;
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
                    ", favoriteReports=" + Arrays.toString(this.favoriteReports) +
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
}
