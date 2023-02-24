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

package me.theentropyshard.pita;

import me.theentropyshard.netschoolapi.NetSchoolAPI;
import me.theentropyshard.pita.view.View;

import javax.swing.*;

public final class Pita {
    private final NetSchoolAPI api;

    private final View view;

    public Pita() {
        if(pita != null) {
            throw new IllegalStateException("Only one Pita instance can exist at a time");
        }
        pita = this;

        this.api = new NetSchoolAPI();

        this.view = new View();
        SwingUtilities.invokeLater(() -> this.view.setVisible(true));

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            synchronized (this.api) {
                this.api.logout();
            }
        }));
    }

    public NetSchoolAPI getAPI() {
        return this.api;
    }

    private static Pita pita;

    public static Pita getPita() {
        return pita;
    }
}
