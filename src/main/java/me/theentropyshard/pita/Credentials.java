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

package me.theentropyshard.pita;

import java.io.Serializable;

public class Credentials implements Serializable {
    private static final long serialVersionUID = 2950531462136080368L;

    private final String schoolAddress;
    private final String schoolName;
    private final String login;
    private final String passwordHash;

    public Credentials(String schoolAddress, String schoolName, String login, String passwordHash) {
        this.schoolAddress = schoolAddress;
        this.schoolName = schoolName;
        this.login = login;
        this.passwordHash = passwordHash;
    }

    public String getSchoolAddress() {
        return this.schoolAddress;
    }

    public String getSchoolName() {
        return this.schoolName;
    }

    public String getLogin() {
        return this.login;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }
}
